package com.jielong.core.service;

import java.util.List;

import com.jielong.core.domain.Carousel;

public interface CarouselImageService {

	public Boolean insert(Carousel carousel);
	
	public List<Carousel> queryCarousels();

	public Boolean deleteCarouselByKey(int id);

	public Boolean forbiddenCarouselByKey(int id);

	public Boolean startCarouselByKey(int id);
}
