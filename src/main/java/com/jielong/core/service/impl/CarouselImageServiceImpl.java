package com.jielong.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CarouselImageDao;
import com.jielong.core.dao.CarouselMapper;
import com.jielong.core.domain.Carousel;
import com.jielong.core.service.CarouselImageService;

@Service
public class CarouselImageServiceImpl implements CarouselImageService{

	@Autowired
	CarouselImageDao carouselImageDao;
	
	@Autowired
	CarouselMapper carouselMapper;
	
	public Boolean insert(Carousel carousel) {
		// TODO Auto-generated method stub
		Boolean call = carouselImageDao.insert(carousel);
		if(call==true){
		return call;
		}else{
			return call;
		}
	}

	@Override
	public List<Carousel> queryCarousels() {
		// TODO Auto-generated method stub
		return carouselMapper.queryCarousels();
	}

	@Override
	public ResponseBean<Integer> deleteCarouselByKey(int id) {
		// TODO Auto-generated method stub
		int i = carouselMapper.deleteByPrimaryKey(id);
		ResponseBean<Integer> call = new ResponseBean<Integer>(i);
		if(i!=0) {
			
		}else {
			call.setErrorMessage("删除失败");
			call.setErrorCode(ErrorCode.DELETE_EXCEPTION);
		}
		return call;
	}

	@Override
	public ResponseBean<Integer> forbiddenCarouselByKey(int id) {
		// TODO Auto-generated method stub
		int i = carouselMapper.forbiddenCarouselByKey(id);
		ResponseBean<Integer> call = new ResponseBean<Integer>(i);
		if(i !=0){
		
		}else {
			call.setErrorCode(ErrorCode.UPDATE_EXCEPTION);
			call.setErrorMessage("禁用轮播图失败");
		}
		return call;
	}

	@Override
	public ResponseBean<Integer> startCarouselByKey(int id) {
		// TODO Auto-generated method stub
		int i = carouselMapper.startCarouselByKey(id);
		ResponseBean<Integer> call = new ResponseBean<Integer>(i);
		if(i !=0){
		
		}else {
			call.setErrorCode(ErrorCode.UPDATE_EXCEPTION);
			call.setErrorMessage("启用轮播图失败");
		}
		return call;
	}




}
