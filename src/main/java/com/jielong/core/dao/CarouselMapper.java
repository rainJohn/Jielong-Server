package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.domain.Carousel;

@Mapper
public interface CarouselMapper {
    
	Integer deleteByPrimaryKey(Integer id);

	Integer insert(Carousel record);

	Integer insertSelective(Carousel record);

    Carousel selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Carousel record);

    Integer updateByPrimaryKey(Carousel record);
    
    List<Carousel> queryCarousels();

    Integer forbiddenCarouselByKey(int id);

    Integer startCarouselByKey(int id);
}