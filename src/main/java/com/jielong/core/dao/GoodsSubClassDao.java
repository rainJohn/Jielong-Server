package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.GoodsSubClass;

@Mapper
public interface GoodsSubClassDao {
  
	/**
	 * 根据父类id查询所有类别
	 * @param parentId
	 * @return
	 */
	@Select("select * from goods_sub_class where parentClassId = #{parentId}")
	public List<GoodsSubClass> findByParentId(@Param("parentId") Integer parentId);
}
