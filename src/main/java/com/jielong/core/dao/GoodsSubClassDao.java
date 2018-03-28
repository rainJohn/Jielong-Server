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
	 * 根据父类id查询所有类别
	 * @param parentId
	 * @return
	 */
	@Select("select * from goods_sub_class where parent_class_id = #{parentId}")
	public List<GoodsSubClass> findByParentId(@Param("parentId") Integer parentId);

	
	/**
	 * 根据父类id删除所有类别
	 * */
	@Delete("delete from goods_sub_class where parent_class_id = #{parentId}")
	public void deleteByParentId(@Param("parentId") Integer parentId);


	/**
	 * 插入新类别
	 * */
	@Insert("insert into goods_sub_class(parent_class_id,class_name,created_at,updated_at) values(#{parentClassId},#{className},now(),now())")
	public ResponseBean<Integer> insert(GoodsSubClass goodsSubClass);


	/**
	 * 修改一条记录
	 * */
	@Update("update goods_sub_class set parent_class_id=#{parentClassId},class_name=#{className},updated_at=#{updatedAt} where id=#{id}")
	public ResponseBean<Integer> update(GoodsSubClass goodsSubClass);


	/**
	 * 删除一条记录
	 * */
	@Delete("delete from goods_sub_class where id = #{id}")
	public ResponseBean<Integer> deleteById(@Param("id") Integer id);
}
