package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.GoodsSubClassDao;
import com.jielong.core.domain.GoodsSubClass;
import com.jielong.core.service.GoodsSubClassService;

@Service
public class GoodsSubClassServiceImpl implements GoodsSubClassService{

	@Autowired
	GoodsSubClassDao goodsSubClassDao;

	@Override
	public ResponseBean<Integer> addGoodsSubClass(GoodsSubClass goodsSubClass) {
		// TODO Auto-generated method stub
		return goodsSubClassDao.insert(goodsSubClass);
	}

	@Override
	public ResponseBean<Integer> updateGoodsSubClass(GoodsSubClass goodsSubClass) {
		// TODO Auto-generated method stub
		goodsSubClass.setUpdatedAt(new Date());
		return goodsSubClassDao.update(goodsSubClass);
	}

	@Override
	public ResponseBean<Integer> deleteGoodsSubClassById(Integer id) {
		// TODO Auto-generated method stub
		return goodsSubClassDao.deleteById(id);
	}
	

}
