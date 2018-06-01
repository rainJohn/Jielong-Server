package com.jielong.core.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jielong.core.domain.ContactUsWebsite;

@Mapper
public interface ContactUsWebsiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContactUsWebsite record);

    int insertSelective(ContactUsWebsite record);

    ContactUsWebsite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContactUsWebsite record);

    int updateByPrimaryKey(ContactUsWebsite record);
}