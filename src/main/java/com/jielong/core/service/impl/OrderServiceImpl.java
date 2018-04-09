package com.jielong.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jielong.base.util.Utils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.domain.Order;
import com.jielong.core.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	CommonDao commonDao;
	@Autowired
	OrderMapper orderMapper;
	
	@Transactional
	@Override
	public ResponseBean<Integer> insert(Order order) {
        String orderNum=Utils.createFileName();
        //订单编号
        order.setOrderNum(orderNum);
        //订单状态
        order.setState(1);
		orderMapper.insertSelective(order);
		return null;
	}
}
