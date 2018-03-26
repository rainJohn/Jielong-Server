package com.jielong.core.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.domain.Carousel;

@Mapper
public interface CarouselImageDao {

	/**
	 * 插入一条记录
	 * @param carouselImageDao
	 */
	@Insert("insert into carousel(carouseladdress) values(#{carouseladdress})")
    Boolean insert(Carousel carousle); 
}
