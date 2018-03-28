package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jielong.core.beans.ResponseBean;
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
	@Insert("insert into goods_class(class_name,created_at,updated_at) values(#{className},now(),now())")
	ResponseBean<Integer> insert(GoodsClass goodsClass);

	/**
	 * 修改一条记录
	 * @param goodsClassDao
	 * */
	@Update("update goods_class set class_name=#{className},updated_at=#{updatedAt} where id=#{id}")
	ResponseBean<Integer> update(GoodsClass goodsClass);

	/**
	 * 删除一条记录
	 * @param goodsClassDao
	 * */
	@Delete("delete from goods_class where id=#{id}")
	ResponseBean<Integer> deleteById(Integer id);


	
    
}
