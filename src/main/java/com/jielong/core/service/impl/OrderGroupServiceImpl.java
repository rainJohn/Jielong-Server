package com.jielong.core.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jielong.base.util.Utils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.GoodsMapper;
import com.jielong.core.dao.OrderGroupConsoleMapper;
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.domain.Goods;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGoods;
import com.jielong.core.domain.OrderGroup;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGroupService;
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
				
		        orderGroupMapper.insert(orderGroupGoods);
		        
		        //取得接龙商品的成团数
		        Goods goods = new  Goods();
		        goods= goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId());
		        int setGroupNum = Integer.valueOf(goods.getGroupSum());
		        
		        int newGroupNum = orderGroupMapper.selectByCustBuyNum(order.getJielongId(), orderGoods.getGoodsId());
		        if(newGroupNum >= setGroupNum) {
		        	//成团状态
		        	//查看ordergroupconsole表的GroupOkFlg状态如果已经是1了
		        	int oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(order.getJielongId(), orderGoods.getGoodsId());
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
		        	int oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(order.getJielongId(), orderGoods.getGoodsId());
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
}
