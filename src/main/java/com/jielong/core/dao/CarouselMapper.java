package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.domain.Carousel;
@Mapper
public interface CarouselMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Carousel record);

    int updateByPrimaryKey(Carousel record);
    
    List<Carousel> queryCarousels();
    
    int forbiddenCarouselByKey(Integer id);
    
    int startCarouselByKey(Integer id);

	List<Carousel> queryStartCarousels();

}