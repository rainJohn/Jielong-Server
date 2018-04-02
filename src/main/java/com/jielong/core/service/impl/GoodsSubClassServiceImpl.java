package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.base.util.ErrorCode;
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
		int i = goodsSubClassDao.insert(goodsSubClass);
		ResponseBean<Integer>  call= new ResponseBean<>(i);
		if(i!=0){}else{
			call.setErrorCode(ErrorCode.INSERT_EXCEPTION);
			call.setErrorMessage("插入失败");
		}
		return call;
	}

	@Override
	public ResponseBean<Integer> updateGoodsSubClass(GoodsSubClass goodsSubClass) {
		// TODO Auto-generated method stub
		goodsSubClass.setUpdatedAt(new Date());
		goodsSubClass.setFlag("0");
		int i = goodsSubClassDao.update(goodsSubClass);
		ResponseBean<Integer>  call= new ResponseBean<>(i);
		if(i!=0){}else{
			call.setErrorCode(ErrorCode.UPDATE_EXCEPTION);
			call.setErrorMessage("更新失败");
		}
		return call;
	}

	@Override
	public ResponseBean<Integer> deleteGoodsSubClassById(Integer id) {
		// TODO Auto-generated method stub
		GoodsSubClass goodsSubClass = goodsSubClassDao.findById(id);
		goodsSubClass.setFlag("1");
		int i =goodsSubClassDao.deleteById(goodsSubClass);
		ResponseBean<Integer>  call= new ResponseBean<>(i);
		if(i!=0){}else{
			call.setErrorCode(ErrorCode.DELETE_EXCEPTION);
			call.setErrorMessage("删除失败");
		}
		return call;
	}
	

}
