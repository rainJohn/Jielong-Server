package com.jielong.core.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.domain.Order;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

  
    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

 

    int updateByPrimaryKey(Order record);
}