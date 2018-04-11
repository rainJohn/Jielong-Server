package com.jielong.core.dao;

import com.jielong.core.domain.OrderGroup;

public interface OrderGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGroup record);

    int insertSelective(OrderGroup record);

    OrderGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGroup record);

    int updateByPrimaryKey(OrderGroup record);
}