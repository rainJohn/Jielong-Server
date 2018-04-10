package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.Order;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

  
    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id); 

    int updateByPrimaryKey(Order record);
    
    @Select("select * from jielong_order where user_id=#{userId}")
    List<Order> selectByUserId(@Param("userId") Integer userId);
}