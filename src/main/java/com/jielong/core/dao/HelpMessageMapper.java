package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.HelpMessage;

@Mapper
public interface HelpMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HelpMessage record);

    int insertSelective(HelpMessage record);

    HelpMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HelpMessage record);

    int updateByPrimaryKey(HelpMessage record);
    
    //查询所有
    @Select("select * from help_message")
    List<HelpMessage> selectAll();
}