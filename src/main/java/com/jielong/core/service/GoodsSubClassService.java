package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.GoodsSubClass;

public interface GoodsSubClassService {

	ResponseBean<Integer> addGoodsSubClass(GoodsSubClass goodsSubClass);

	ResponseBean<Integer> updateGoodsSubClass(GoodsSubClass goodsSubClass);

	ResponseBean<Integer> deleteGoodsSubClassById(Integer id);

}
