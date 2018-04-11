package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Order;


public interface OrderGroupService {
  ResponseBean<Integer> insert(Order order);
  
  
}
