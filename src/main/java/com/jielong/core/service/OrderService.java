package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Order;

public interface OrderService {
	ResponseBean<Integer> insert(Order order);
}
