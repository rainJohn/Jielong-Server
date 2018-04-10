package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.OrderGoods;

public interface OrderGoodsService {
  ResponseBean<Integer> insert(OrderGoods orderGoods);
}
