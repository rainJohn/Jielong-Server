package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Order;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderGroupService orderGroupService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody Order order){
		Integer isSetGroup=order.getIsSetGroup();
		if (isSetGroup==1) {
			return orderGroupService.insert(order);
		}else {
			return orderService.insert(order);
		}
	    	
	}
	
	/**
	 * 根据用户id查询参与的接龙
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/selectByCustomerId")
	public ResponseBean<List<Order>> selectByCustomerId(@RequestParam("customerId") Integer customerId){
	   return orderService.selectByCustomerId(customerId);  	
		
	}
	
	

}
