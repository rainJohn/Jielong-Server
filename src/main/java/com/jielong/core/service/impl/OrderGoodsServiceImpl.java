package com.jielong.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.OrderGoodsMapper;
import com.jielong.core.domain.OrderGoods;
import com.jielong.core.service.OrderGoodsService;

@Service
public class OrderGoodsServiceImpl implements OrderGoodsService{
	@Autowired
	OrderGoodsMapper orderGoodsMapper;
	
	
	@Override
	public ResponseBean<Integer> insert(OrderGoods orderGoods) {
		
		Integer result=orderGoodsMapper.insertSelective(orderGoods);
		return new ResponseBean<Integer>(result);
	}

}
