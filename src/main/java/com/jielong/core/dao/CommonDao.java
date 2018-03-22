package com.jielong.core.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommonDao {
  
	/**
	 * 获取最新插入的id
	 * @return
	 */
	@Select("SELECT LAST_INSERT_ID()")
	Integer getLastId();
}
