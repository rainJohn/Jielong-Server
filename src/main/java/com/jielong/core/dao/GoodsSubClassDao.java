package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.GoodsSubClass;

@Mapper
public interface GoodsSubClassDao {
  
	/**
	 * 根据主键查找
	 * */
	@Select("select * from goods_sub_class where id = #{id}")
	public GoodsSubClass findById(Integer id);
	/**
	 * 根据父类id查询所有类别
	 * @param parentId
	 * @return
	 */
	@Select("select * from goods_sub_class where parent_class_id = #{parentId}")
	public List<GoodsSubClass> findByParentId(@Param("parentId") Integer parentId);

	
	/**
	 * 根据id假删除所有类别
	 * @return 
	 * */
	@Update("update goods_sub_class set parent_class_id=#{parentClassId},class_name=#{className},updated_at=#{updatedAt},flag=#{flag} where id=#{id}")
	public Integer deleteById(GoodsSubClass goodsSubClass);
	

	/**
	 * 插入新类别
	 * */
	@Insert("insert into goods_sub_class(parent_class_id,class_name,created_at,updated_at,flag) values(#{parentClassId},#{className},now(),now(),#{flag})")
	public Integer insert(GoodsSubClass goodsSubClass);


	/**
	 * 修改一条记录
	 * */
	@Update("update goods_sub_class set parent_class_id=#{parentClassId},class_name=#{className},updated_at=#{updatedAt},flag=#{flag} where id=#{id}")
	public Integer update(GoodsSubClass goodsSubClass);


	
}
