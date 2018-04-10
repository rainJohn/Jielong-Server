package com.jielong.core.service;

import java.util.List;
import java.util.Map;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Carousel;

public interface CarouselImageService {

	public ResponseBean<Integer> insert(Carousel carousel);
	
	public List<Carousel> queryCarousels();

	public ResponseBean<Integer> deleteCarouselByKey(int id);

	public ResponseBean<Integer> forbiddenCarouselByKey(int id);

	public ResponseBean<Integer> startCarouselByKey(int id);

	public List<Carousel> queryStartCarousels();

	public ResponseBean<Integer> addRemarkById(int id, String remark);
}
