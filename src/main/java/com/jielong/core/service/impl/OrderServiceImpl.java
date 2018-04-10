package com.jielong.core.service.impl;

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
import com.jielong.core.service.OrderGoodsService;
import com.jielong.core.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	CommonDao commonDao;
	@Autowired
	OrderMapper orderMapper;	
	@Autowired
	OrderGoodsService orderGoodsService;
	
	@Transactional
	@Override
	public ResponseBean<Integer> insert(Order order) {
        String orderNum=Utils.createFileName();
        //订单编号
        order.setOrderNum(orderNum);
        //订单状态
        order.setState(1);
		orderMapper.insertSelective(order);		
		Integer orderId=commonDao.getLastId();
		List<OrderGoods> orderGoodsList=order.getOrderGoods();
		if (orderGoodsList!=null && orderGoodsList.size()>0) {
			for(int i=0;i<orderGoodsList.size();i++) {
			   OrderGoods orderGoods=orderGoodsList.get(i);
			   orderGoods.setOrderId(orderId);
			   orderGoodsService.insert(orderGoods);
			}
		}
		
		
		
		return null;
	}
}
