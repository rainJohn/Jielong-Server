package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.GoodsClass;

public interface GoodsClassService {
	
	List<GoodsClass> selectAllClass();

	ResponseBean<Integer> addGoodsClass(GoodsClass goodsClass);

	ResponseBean<Integer> updateGoodsClass(GoodsClass goodsClass);

	ResponseBean<Integer> deleteGoodsClassById(Integer id);


}
