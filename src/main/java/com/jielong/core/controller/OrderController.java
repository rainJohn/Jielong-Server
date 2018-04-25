package com.jielong.core.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.PickBean;
import com.jielong.core.beans.PickCountBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.beans.SignPickBean;
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
	public ResponseBean<Integer> insert(@RequestBody Order order) {
		Integer isSetGroup = order.getIsSetGroup();
		if (isSetGroup == 1) {
			return orderGroupService.insert(order);
		} else {
			return orderService.insert(order);
		}

	}

	/**
	 * 根据顾客id查询参与的接龙
	 * 
	 * @param customerId
	 * @return
	 */
	@RequestMapping("/selectByCustomerId")
	public ResponseBean<List<Order>> selectByCustomerId(@RequestParam("customerId") Integer customerId) {
		List<Order> orderList = new ArrayList<Order>();
		List<Order> orderList1 = orderService.selectByCustomerId(customerId).getData();
		List<Order> orderList2 = orderGroupService.selectByCustomerId(customerId).getData();
		if (orderList1 != null) {
			orderList.addAll(orderList1);
		}
		if (orderList2 != null) {
			orderList.addAll(orderList2);
		}
		orderList=orderList.stream().sorted(Comparator.comparing(Order::getCreatedAt).reversed()).collect(Collectors.toList());
		return new ResponseBean<List<Order>>(orderList);
	}

	/**
	 * 自提标记
	 * 
	 * @param orderNumList
	 *            订单列表
	 * @return 受影响记录数
	 */
	@RequestMapping("/signPick")
	public ResponseBean<Integer> signPick(@RequestBody SignPickBean signPickBean) {
		int result = 0;
		Integer result1 = orderService.signPick(signPickBean).getData();
		Integer result2 = orderGroupService.signPick(signPickBean).getData();
		if (result1 != null) {
			result += result1;
		}
		if (result2 != null) {
			result += result2;
		}
		return new ResponseBean<Integer>(result);
	}

	/**
	 * 查询所有待提货和已提货的订单
	 * 
	 * @param jielongId
	 * @return
	 */
	@RequestMapping("/selectPickOrder")
	public ResponseBean<List<Order>> selectOrder(@RequestParam("jielongId") Integer jielongId) {
		List<Order> orderList1 = orderService.selectByJielongId(jielongId).getData();
		List<Order> orderList2 = orderGroupService.selectPickByJielongId(jielongId).getData();
		List<Order> orderList = new ArrayList<Order>();
		if (orderList1 != null) {
			orderList.addAll(orderList1.stream().filter(order -> order.getState() == 2 || order.getState()==3).collect(Collectors.toList()));
		}
		if (orderList2 != null) {
			orderList.addAll(orderList2);
		}

		return new ResponseBean<List<Order>>(orderList);
	}

	// 接龙统计
	@RequestMapping("/pickCount")
	public ResponseBean<List<PickCountBean>> pickCount(@RequestParam("jielongId") Integer jielongId,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		
		List<PickCountBean> groupList = orderGroupService.countPick(jielongId).getData();
		List<PickCountBean> directList = orderService.countPick(jielongId).getData();

		List<PickCountBean> countList = new ArrayList<PickCountBean>();
		
		if (directList != null && directList.size() > 0) {
			for(int i=0;i<directList.size();i++) {
			   PickCountBean pickCountBean=directList.get(i);
			   
			   if (pickCountBean.getPickBeans() != null) {
					List<PickBean> pickBeans = pickCountBean.getPickBeans();
					Integer sellSum=0;
					Integer joinPeopleSum=0;
					BigDecimal moneySum=new BigDecimal(0);
					if (startTime != null && endTime != null) {
						pickBeans = pickCountBean.getPickBeans().stream()
								.filter(pickBean -> startTime.compareTo(pickBean.getCreatedAt()) < 0)
								.filter(pickBean -> endTime.compareTo(pickBean.getCreatedAt()) > 0)
								.collect(Collectors.toList());

					}
					for(PickBean pickBean: pickBeans) { 
						joinPeopleSum+=1;
						sellSum+=pickBean.getGoodsSum();
						moneySum=moneySum.add(pickBean.getPrice().multiply(new BigDecimal(pickBean.getGoodsSum())));
						
					}
					pickCountBean.setMoneySum(moneySum);
					pickCountBean.setSellSum(sellSum);
					pickCountBean.setJoinPeopleSum(joinPeopleSum);
					pickCountBean.setPickBeans(pickBeans);

				}
			   PickCountBean pickCountBean2=groupList.get(i);
			   if (pickCountBean2.getPickBeans() != null) {
					List<PickBean> pickBeans = pickCountBean2.getPickBeans();
					Integer sellSum=0;
					Integer joinPeopleSum=0;
					BigDecimal moneySum=new BigDecimal(0);
					if (startTime != null && endTime != null) {
						pickBeans = pickCountBean2.getPickBeans().stream()
								.filter(pickBean -> startTime.compareTo(pickBean.getCreatedAt()) < 0)
								.filter(pickBean -> endTime.compareTo(pickBean.getCreatedAt()) > 0)
								.collect(Collectors.toList());

					}
					for(PickBean pickBean: pickBeans) { 
						joinPeopleSum+=1;
						sellSum+=pickBean.getGoodsSum();
						moneySum=moneySum.add(pickBean.getPrice().multiply(new BigDecimal(pickBean.getGoodsSum())));
						
					}
					pickCountBean.setMoneySum(moneySum);
					pickCountBean.setSellSum(sellSum);
					pickCountBean.setJoinPeopleSum(joinPeopleSum);
					pickCountBean.setPickBeans(pickBeans);

				}
			  
				countList.add(pickCountBean);
			   
			}

		
		}
		return new ResponseBean<List<PickCountBean>>(countList);

	}
	
	//取消参团
	@RequestMapping("/cancelJoinGroup")
    public ResponseBean<Integer> cancelJoinGroup(@RequestBody Order order){
		
		return orderGroupService.cancelJoinGroup(order);
	}	

}
