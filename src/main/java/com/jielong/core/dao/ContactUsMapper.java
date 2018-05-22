package com.jielong.core.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.domain.ContactUs;

@Mapper
public interface ContactUsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContactUs record);

    int insertSelective(ContactUs record);

    ContactUs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContactUs record);

    int updateByPrimaryKey(ContactUs record);
}