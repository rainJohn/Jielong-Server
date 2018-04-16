package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.PickCountBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Order;

public interface OrderService {
	ResponseBean<Integer> insert(Order order);
	
	ResponseBean<List<Order>> selectByCustomerId(Integer userId);
	
	ResponseBean<List<Order>> selectByPublisherId(Integer userId);
	
	ResponseBean<List<Order>> selectByJielongId(Integer jielongId);
	
	ResponseBean<Integer> signPick(List<String> orderNumList);
	
	//Jielong统计
	ResponseBean<List<PickCountBean>>  countPick(Integer jielongId);
	
	Order selectById(Integer id);
}
