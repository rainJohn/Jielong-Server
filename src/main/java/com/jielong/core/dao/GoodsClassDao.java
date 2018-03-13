package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.GoodsClass;

@Mapper
public interface  GoodsClassDao{

	/**
	 * 返回所有父类别
	 * @return
	 */
	@Select("Select * from goods_class")
	List<GoodsClass> selectAllClasses() ;
	
	/**
	 * 插入一条记录
	 * @param goodsClassDao
	 */
	@Insert("insert into goods_class(className,createdAt,updatedAt) values(#{className},now(),now())")
    void insert(GoodsClass goodsClass); 
	
	/**
	 * 根据id查询单条记录
	 * @param id
	 * @return
	 */
	GoodsClass selectById(Integer id);
}
