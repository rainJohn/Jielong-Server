package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Order;


public interface OrderGroupService {
  ResponseBean<Integer> insert(Order order);
  
  ResponseBean<List<Order>> selectByCustomerId(Integer userId);
	
  ResponseBean<List<Order>> selectByPublisherId(Integer userId);
}
