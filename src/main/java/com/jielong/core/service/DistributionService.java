package com.jielong.core.service;

import com.jielong.core.domain.Distribution;
import com.jielong.core.domain.Order;

import java.util.List;

public interface DistributionService {

   int insert(Order order);

   List<Distribution> selectByUserId(Integer userId);

}
