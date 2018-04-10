package com.jielong.core.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jielong.base.util.Utils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGoods;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGoodsService;
import com.jielong.core.service.OrderService;
import com.jielong.core.service.UserMessageService;


@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	CommonDao commonDao;
	@Autowired
	OrderMapper orderMapper;	
	@Autowired
	OrderGoodsService orderGoodsService;	
	@Autowired
	JielongService jielongService;
	@Autowired
	UserMessageService userMessageService;
	
	@Transactional
	@Override
	public ResponseBean<Integer> insert(Order order) {
		ResponseBean<Integer> responseBean=new ResponseBean<>();
		
		//订单内商品列表
		List<OrderGoods> orderGoodsList=order.getOrderGoods();
		
        String orderNum=Utils.createFileName();
        //订单编号
        order.setOrderNum(orderNum);
        //订单状态
        order.setState(1);
        //订单总金额······················
        BigDecimal sumMoney=new BigDecimal(0);
        if (orderGoodsList!=null && orderGoodsList.size()>0) {
        	 for(int j=0;j<orderGoodsList.size();j++) {
                 BigDecimal price=orderGoodsList.get(j).getMoney();
                 Integer sum=orderGoodsList.get(j).getSum();
                 BigDecimal tempMOney=price.multiply(new BigDecimal(sum));
                 
                 sumMoney.add(tempMOney);        	
               }        
		}
       
        order.setSumMoney(sumMoney);        
        
		orderMapper.insertSelective(order);		
		Integer orderId=commonDao.getLastId();
		 	                       
		
		if (orderGoodsList!=null && orderGoodsList.size()>0) {
			for(int i=0;i<orderGoodsList.size();i++) {
			   OrderGoods orderGoods=orderGoodsList.get(i);
			   orderGoods.setOrderId(orderId);
			   orderGoodsService.insert(orderGoods);
			}
		}
		
		//下单之后，更新接龙参与人数、参与金额等信息	
		jielongService.updateJoin(order.getJielongId(), sumMoney);
		
		//下单之后给用户发送消息
		UserMessage userMessage=new UserMessage();
		userMessage.setTitle("下单成功通知！");
		userMessage.setMessage("你已成功下单，请尽快上门提货！订单详情请前往我的->我参与的接龙查看。");
		userMessageService.insert(userMessage);
		
		responseBean.setData(1);
		return responseBean;
	}
}
