package com.jielong.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jielong.base.util.Utils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.GoodsMapper;
import com.jielong.core.dao.JielongMapper;
import com.jielong.core.dao.OrderGroupConsoleMapper;
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.domain.Goods;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGoods;
import com.jielong.core.domain.OrderGroup;
import com.jielong.core.domain.UserAddress;
import com.jielong.core.domain.UserInfo;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.UserAddressService;
import com.jielong.core.service.UserInfoService;
import com.jielong.core.service.UserMessageService;


@Service
public class OrderGroupServiceImpl implements OrderGroupService{

	@Autowired
	CommonDao commonDao;
	@Autowired
	OrderMapper orderMapper;	
	@Autowired
	OrderGroupConsoleMapper orderGroupConsoleMapper;
	@Autowired
	OrderGroupMapper orderGroupMapper;
	@Autowired
	GoodsMapper goodsMapper;
	@Autowired
	JielongService jielongService;
	@Autowired
	UserMessageService userMessageService;
	@Autowired
	UserAddressService userAddressService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	JielongMapper jielongMapper;
	
	
	@Transactional
	@Override
	public ResponseBean<Integer> insert(Order order) {
		ResponseBean<Integer> responseBean  = new ResponseBean<Integer>();
        String orderNum=Utils.createFileName();
        List<OrderGoods> orderGoodsList=order.getOrderGoods();
		if (orderGoodsList!=null && orderGoodsList.size()>0) {
			for(int i=0;i<orderGoodsList.size();i++) {
				OrderGroup orderGroupGoods = new OrderGroup();
				//订单编号
		        orderGroupGoods.setOrderId(orderNum);
		        //接龙ID
		        orderGroupGoods.setJielongId(order.getJielongId());
		        //购买者ID
		        orderGroupGoods.setCustId(order.getUserId());
		        orderGroupGoods.setCustName(order.getUserName());
		        orderGroupGoods.setCustPhone(order.getUserPhone());
		        orderGroupGoods.setCustNote(order.getRemark());
		        orderGroupGoods.setAddressId(order.getAddressId());
		        		        
		        OrderGoods orderGoods=orderGoodsList.get(i);
		        orderGroupGoods.setGoodsId(orderGoods.getGoodsId());
		        orderGroupGoods.setCustBuyNum(orderGoods.getSum());
		        orderGroupGoods.setCustBuyPrice(orderGoods.getMoney());
		        BigDecimal buyAllMoney = new BigDecimal(0);
		        buyAllMoney = orderGoods.getMoney().multiply(new BigDecimal(orderGoods.getSum()));
		        orderGroupGoods.setCustBuyAllMoney(buyAllMoney);
		        //交易状态
		        orderGroupGoods.setTradeFlg(0);
				//订单状态
		        orderGroupGoods.setOrderFlg(0);
				
		        orderGroupMapper.insertSelective(orderGroupGoods);
		        
		        //取得接龙商品的成团数
		        Goods goods = new  Goods();
		        goods= goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId());
		        int setGroupNum = Integer.valueOf(goods.getGroupSum());
		        
		        int newGroupNum = orderGroupMapper.selectByCustBuyNum(order.getJielongId(), orderGoods.getGoodsId());
		        if(newGroupNum >= setGroupNum) {
		        	//成团状态
		        	//查看ordergroupconsole表的GroupOkFlg状态如果已经是1了
		        	Integer oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(order.getJielongId(), orderGoods.getGoodsId());
		        	if (oldGroupOkFlg == 1)
		        	{
		        		//发送单人通知 已经是成功的团了。
						UserMessage userMessage=new UserMessage();
						userMessage.setUserId(order.getUserId());
						userMessage.setTitle("下单成功通知！");
						userMessage.setMessage("你已成功下单，拼团成功，请尽快上门提货！订单详情请前往我的->我参与的接龙查看。");
						userMessageService.insert(userMessage);
		        	} else {
		        		//恭喜终于成团了。
		        		//更新ordergroupconsole表
			        	int updateret = orderGroupConsoleMapper.updateGroupOkFlg(1, order.getJielongId(), orderGoods.getGoodsId());
			        	
			        	//下单之后给用户发送消息
			        	userMessageService.groupStateModify(order.getJielongId(), orderGoods.getGoodsId(), 1);
		        	}
		        	
					
		        } else {
		        	//成团状态
		        	//查看ordergroupconsole表的GroupOkFlg状态如果已经是1了
		        	Integer oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(order.getJielongId(), orderGoods.getGoodsId());
		        	if (oldGroupOkFlg!=null) {
		        		if (oldGroupOkFlg == 1){
			        		//有撤单的情况!从成团变成了 未成团。群发通知
			        		int updateret = orderGroupConsoleMapper.updateGroupOkFlg(0, order.getJielongId(), orderGoods.getGoodsId());
				        	
				        	//下单之后给用户发送消息
				        	userMessageService.groupStateModify(order.getJielongId(), orderGoods.getGoodsId(), 0);
			        		
			        	} else {
			        		//发送单人通知
			        		//下单之后给用户发送消息
							UserMessage userMessage=new UserMessage();
							userMessage.setUserId(order.getUserId());
							userMessage.setTitle("下单成功通知！");
							userMessage.setMessage("你已成功下单，拼团人数不足，请等候！订单详情请前往我的->我参与的接龙查看。");
							userMessageService.insert(userMessage);
			        	}
					}
		        	
		        	
		        }
		        //减少对应商品的库存
				goodsMapper.updateRepertory(orderGoods.getGoodsId(), orderGoods.getSum());
		        //下单之后，更新接龙参与人数、参与金额等信息	
				jielongService.updateJoin(order.getJielongId(), buyAllMoney);

			}
			responseBean.setData(1);
			return responseBean;
		}
        
		return null;
	}
	
	
	    //根据顾客id查询订单(参与的接龙)
		@Transactional
		@Override
		public ResponseBean<List<Order>> selectByCustomerId(Integer userId) {
	        ResponseBean<List<Order>> responseBean=new ResponseBean<List<Order>>();
	        List<OrderGroup> orderGroupList= orderGroupMapper.selectByCustId(userId);
	         //转换输出格式
	        List<Order> orderList = new ArrayList<Order>();
	        if (orderGroupList!=null&&orderGroupList.size()>0) {
	        	for(OrderGroup ordergroup : orderGroupList) {
	        	 //转换输出格式
	        	  Order order = new Order();
	        	  order.setId(ordergroup.getId());
	        	  order.setIsSetGroup(1);
	        	  order.setJielongId(ordergroup.getJielongId());
	        	  order.setOrderNum(ordergroup.getOrderId());
	        	  order.setRemark(ordergroup.getCustNote());
	        	  order.setState(ordergroup.getTradeFlg());
	        	  order.setSumMoney(ordergroup.getCustBuyAllMoney());
//	        	  order.setUserAddress(userAddress);
	        	  order.setUserId(ordergroup.getCustId());
//	        	  order.setUserInfo(ordergroup.get);
	        	  order.setUserName(ordergroup.getCustName());
	        	  order.setUserPhone(ordergroup.getCustPhone());
	        	  order.setAddressId(ordergroup.getAddressId());
	        	  order.setCreatedAt(ordergroup.getCreatedAt());
	        	  order.setUpdatedAt(ordergroup.getUpdatedAt());
	        	  
	        	  //Jielong主题
		          String topic=jielongMapper.selectTopic(ordergroup.getJielongId());
		          order.setJielongTopic(topic);	
	        	  //提货地址信息
				  Integer addressId=ordergroup.getAddressId();
	        	  UserAddress address=userAddressService.selectById(addressId).getData();
	        	  order.setUserAddress(address);
	        	  //用户信息
	        	  Integer  clientId=ordergroup.getCustId();
	        	  UserInfo userInfo=userInfoService.selectByUserId(clientId).getData();
	        	  order.setUserInfo(userInfo);
	        	  //订单商品信息        	  
	        	  List<OrderGroup> orderGroupList2=orderGroupMapper.selectByOrderId(ordergroup.getOrderId());
	        	  
	        	  if (orderGroupList2!=null && orderGroupList2.size()>0) {
	        		  List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
	        		  for (OrderGroup orderGroup2 : orderGroupList2) {
	    				 
	                      //对应统一接口 ORDER
	        			  Goods goods= goodsMapper.selectByPrimaryKey(orderGroup2.getGoodsId());
	                      OrderGoods orderGoods = new OrderGoods();
	                      orderGoods.setGoods(goods);
	                      orderGoods.setGoodsId(orderGroup2.getGoodsId());
//	                      orderGoods.setId();
	                      orderGoods.setMoney(orderGroup2.getCustBuyPrice());
//	                      orderGoods.setOrderId(orderGroup.getOrderId());
	                      orderGoods.setSum(orderGroup2.getCustBuyNum());
	                      
	                      //商品成功成团与否FLG
	                      Integer groupOkFlg = orderGroupConsoleMapper.selectGroupOkState(orderGroup2.getJielongId(), orderGroup2.getGoodsId());
	                      if(groupOkFlg == 1)
	                      {
	                    	  //参团成功
	                    	  orderGoods.setGroupFlg(groupOkFlg);
	                    	  orderGoods.setJoinGroupNum(0);
	                      } else {
	                    	  orderGoods.setGroupFlg(groupOkFlg);
	                    	  //参团不成功，差几人计算
	                    	  int setGroupNum = Integer.valueOf(goods.getGroupSum());
	          		        
	          		          int newGroupNum = orderGroupMapper.selectByCustBuyNum(orderGroup2.getJielongId(), orderGroup2.getGoodsId());
	          		          
	          		          int numtmp = setGroupNum - newGroupNum;
	          		          orderGoods.setJoinGroupNum(numtmp);
	                      }                     
	                      
	                      
	                      orderGoodsList.add(orderGoods);
	    			   }
	        		  order.setOrderGoods(orderGoodsList);
				   }     
	        	  orderList.add(order);
	        		
	        	}			
			}
	        
	        responseBean.setData(orderList);
			return responseBean;
		}
		
		/**
		 * 根据发布者id查询订单
		 */
		@Transactional
		@Override
		public ResponseBean<List<Order>> selectByPublisherId(Integer userId) {
			  ResponseBean<List<Order>> responseBean=new ResponseBean<List<Order>>();
			  List<OrderGroup> orderGroupList= orderGroupMapper.selectByPublisherId(userId);
		      //转换输出格式
		        List<Order> orderList = new ArrayList<Order>();
		        
		        if (orderGroupList!=null&&orderGroupList.size()>0) {
		        	for(OrderGroup orderGroup : orderGroupList) {
		        		//转换输出格式
			        	  Order order = new Order();
			        	  order.setId(orderGroup.getId());
			        	  order.setIsSetGroup(1);
			        	  order.setJielongId(orderGroup.getJielongId());
//			        	  order.setJielongTopic(ordergroup);
//			        	  order.setOrderGoods(ordergroup);
			        	  order.setOrderNum(orderGroup.getOrderId());
			        	  order.setRemark(orderGroup.getCustNote());
			        	  order.setState(orderGroup.getTradeFlg());
			        	  order.setSumMoney(orderGroup.getCustBuyAllMoney());
//			        	  order.setUserAddress(userAddress);
			        	  order.setUserId(orderGroup.getCustId());
//			        	  order.setUserInfo(ordergroup.get);
			        	  order.setUserName(orderGroup.getCustName());
			        	  order.setUserPhone(orderGroup.getCustPhone());
			        	  order.setCreatedAt(orderGroup.getCreatedAt());
			        	  order.setUpdatedAt(orderGroup.getUpdatedAt());

			        	  order.setAddressId(orderGroup.getAddressId());

			        	  
			        	  //Jielong主题
				          String topic=jielongMapper.selectTopic(orderGroup.getJielongId());
				          order.setJielongTopic(topic);	
			        	  //提货地址信息
						  Integer addressId=orderGroup.getAddressId();
			        	  UserAddress address=userAddressService.selectById(addressId).getData();
			        	  order.setUserAddress(address);
		        	      //用户信息
			        	  Integer  clientId=orderGroup.getCustId();
			        	  UserInfo userInfo=userInfoService.selectByUserId(clientId).getData();
			        	  order.setUserInfo(userInfo);
			        	  
			        	  //订单商品信息        	  
			        	  List<OrderGroup> orderGroupList2=orderGroupMapper.selectByOrderId(orderGroup.getOrderId());
			        	  
			        	  if (orderGroupList2!=null && orderGroupList2.size()>0) {
			        		  List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
			        		  for (OrderGroup orderGroup2 : orderGroupList2) {
			    				 
			                      //对应统一接口 ORDER
			        			  Goods goods= goodsMapper.selectByPrimaryKey(orderGroup2.getGoodsId());
			                      OrderGoods orderGoods = new OrderGoods();
			                      orderGoods.setGoods(goods);
			                      orderGoods.setGoodsId(orderGroup2.getGoodsId());
//			                      orderGoods.setId();
			                      orderGoods.setMoney(orderGroup2.getCustBuyPrice());
//			                      orderGoods.setOrderId(orderGroup.getOrderId());
			                      orderGoods.setSum(orderGroup2.getCustBuyNum());
			                      
			                      //商品成功成团与否FLG
			                      Integer groupOkFlg = orderGroupConsoleMapper.selectGroupOkState(orderGroup2.getJielongId(), orderGroup2.getGoodsId());
			                      
			                      if(groupOkFlg == 1)
			                      {
			                    	  //参团成功
			                    	  orderGoods.setGroupFlg(groupOkFlg);
			                    	  orderGoods.setJoinGroupNum(0);
			                      } else {
			                    	  orderGoods.setGroupFlg(groupOkFlg);
			                    	  //参团不成功，差几人计算
			                    	  int setGroupNum = Integer.valueOf(goods.getGroupSum());
			          		        
			          		          int newGroupNum = orderGroupMapper.selectByCustBuyNum(orderGroup2.getJielongId(), orderGroup2.getGoodsId());
			          		          
			          		          int numtmp = setGroupNum - newGroupNum;
			          		          orderGoods.setJoinGroupNum(numtmp);
			                      }
			                      
			                      
			                      
			                      orderGoodsList.add(orderGoods);
			    			   }
			        		  order.setOrderGoods(orderGoodsList);
			        	  }
			        	  orderList.add(order);
		        	}			
				}
		        
		        responseBean.setData(orderList);
				return responseBean;
		}
		
		@Override
		//计算已参团数量
		public int getGroupPeople(Integer jielongId,Integer goodsId) {
			int peopleSum=0;
			 //商品成功成团与否FLG
            Integer groupOkFlg = orderGroupConsoleMapper.selectGroupOkState(jielongId, goodsId);
            if (groupOkFlg!=null) {          	
               	 
               	  //参团不成功，计算已参团数量  	    
     		        
     		      Integer num = orderGroupMapper.selectByCustBuyNum(jielongId, goodsId);
     		      if (num!=null) {
     		        	 peopleSum=num;
					}         
     		         
                 
			}          
            
            return peopleSum;
            
		} 
		
		@Override
		public ResponseBean<List<Order>> selectByJielongId(Integer jielongId) {

			return null;
		}
}
