package com.jielong.core.dao;

import com.jielong.core.domain.OrderGroupConsole;

public interface OrderGroupConsoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGroupConsole record);

    int insertSelective(OrderGroupConsole record);

    OrderGroupConsole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGroupConsole record);

    int updateByPrimaryKey(OrderGroupConsole record);
}