package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.Jielong;

@Mapper
public interface JielongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jielong record);

    int insertSelective(Jielong record);

    Jielong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jielong record);

    int updateByPrimaryKey(Jielong record);
    
    @Select("select * from jielong")
    List<Jielong> selectAll();
    
    @Select("select * from jielong where user_id= #{userId}")
    List<Jielong> selectByUserId(@Param("userId") Integer userId);
    
   
}