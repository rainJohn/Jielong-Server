package com.jielong.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.dao.CarouselImageDao;
import com.jielong.core.domain.Carousel;
import com.jielong.core.service.CarouselImageService;

@Service
public class CarouselImageServiceImpl implements CarouselImageService{

	@Autowired
	CarouselImageDao carouselImageDao;
	
	public Boolean insert(Carousel carousel) {
		// TODO Auto-generated method stub
		Boolean call = carouselImageDao.insert(carousel);
		if(call==true){
		return call;
		}else{
			return call;
		}
	}

}
