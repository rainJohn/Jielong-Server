package com.jielong.core.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jielong.base.util.Utils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGoods;
import com.jielong.core.domain.OrderGroup;
import com.jielong.core.domain.OrderGroupConsole;

import com.jielong.core.service.OrderGroupService;


@Service
public class OrderGroupServiceImpl implements OrderGroupService{

	@Autowired
	CommonDao commonDao;
	@Autowired
	OrderMapper orderMapper;	
	@Autowired
	OrderGroupConsole orderGroupConsole;
	@Autowired
	OrderGroupMapper orderGroupMapper;

	
	
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
		        
		        
		        
//			    orderGoodsService.insert(orderGoods);
			}
			responseBean.setData(1);
		}
        
		return null;
	}
}
