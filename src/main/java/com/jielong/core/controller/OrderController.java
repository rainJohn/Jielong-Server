package com.jielong.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.PickCountBean;
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
	 * 根据顾客id查询参与的接龙
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/selectByCustomerId")
	public ResponseBean<List<Order>> selectByCustomerId(@RequestParam("customerId") Integer customerId){
		List<Order> orderList=new ArrayList<Order>();
	    List<Order> orderList1=orderService.selectByCustomerId(customerId).getData();  	
		List<Order> orderList2=orderGroupService.selectByCustomerId(customerId).getData();
		if (orderList1!=null) {
			orderList.addAll(orderList1);
		}
		if (orderList2!=null) {
			orderList.addAll(orderList2);
		}
		return new ResponseBean<List<Order>>(orderList);
	}
	/**
	 * 自提标记
	 * @param orderNumList 订单列表
	 * @return 受影响记录数
	 */
	@RequestMapping("/signPick")
	public ResponseBean<Integer> signPick(@RequestBody List<String> orderNumList){
		int result=0;
		Integer result1=orderService.signPick(orderNumList).getData();
		Integer result2=orderGroupService.signPick(orderNumList).getData();
		if (result1!=null) {
			result+=result1;
		}if (result2!=null) {
			result+=result2;
		}
		return new ResponseBean<Integer>(result);
	}
	
	/**
	 * 查询所有待提货的订单
	 * @param jielongId
	 * @return
	 */
	@RequestMapping("/selectPickOrder")
	public ResponseBean<List<Order>> selectOrder(@RequestParam("jielongId") Integer jielongId){
	     List<Order> orderList1=orderService.selectByJielongId(jielongId).getData(); 
	     List<Order> orderList2=orderGroupService.selectPickByJielongId(jielongId).getData();
	     List<Order> orderList=new ArrayList<Order>();
	     if (orderList1!=null) {
			orderList.addAll(orderList1.stream().filter(order->order.getState()==2).collect(Collectors.toList()));
		 }
	     if (orderList2!=null) {
	    	 orderList.addAll(orderList2);
		}
	     
	     return new ResponseBean<List<Order>>(orderList);
	}
	
	@RequestMapping("/pickCount")
	public ResponseBean<List<PickCountBean>> pickCount(@RequestParam("jielongId") Integer jielongId){
		
		return orderService.countPick(jielongId);
		
	}

}
