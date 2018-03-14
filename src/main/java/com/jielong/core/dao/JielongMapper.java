package com.jielong.core.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.domain.Jielong;

@Mapper
public interface JielongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jielong record);

    int insertSelective(Jielong record);

    Jielong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jielong record);

    int updateByPrimaryKey(Jielong record);
}