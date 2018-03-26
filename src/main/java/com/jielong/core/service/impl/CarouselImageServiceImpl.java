package com.jielong.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Boolean deleteCarouselByKey(int id) {
		// TODO Auto-generated method stub
		int i = carouselMapper.deleteByPrimaryKey(id);
		if(i != 0){
		return true;
		}
		return false;
	}

	@Override
	public Boolean forbiddenCarouselByKey(int id) {
		// TODO Auto-generated method stub
		int i = carouselMapper.forbiddenCarouselByKey(id);
		if(i !=0){
		return true;
		}
		return false;
	}

	@Override
	public Boolean startCarouselByKey(int id) {
		// TODO Auto-generated method stub
		int i = carouselMapper.startCarouselByKey(id);
		if(i !=0){
		return true;
		}
		return false;
	}




}
