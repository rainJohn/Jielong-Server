package com.jielong.core.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Carousel;

@Mapper
public interface CarouselImageDao {

	/**
	 * 插入一条记录
	 * @param carouselImageDao
	 */
	@Insert("insert into carousel(carouseladdress,createtime,updatetime) values(#{carouseladdress},now(),now())")
	Integer insert(Carousel carousle); 
}
