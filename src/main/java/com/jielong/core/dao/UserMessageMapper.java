package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jielong.core.domain.UserMessage;

@Mapper
public interface UserMessageMapper {
	
    int deleteByPrimaryKey(Integer id); 

    int insertSelective(UserMessage record);

    UserMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMessage record);
    
    @Select("select * from user_message")
    List<UserMessage> selectAll();
    
    @Update("update user_message set is_read=1,update_time=now() where id=#{id}")
    int updateReadState(@Param("id") Integer id); 
    
    //批量插入
    int insertBatch(UserMessage userMessage);
    
    //根据用户id查询
    @Select("select * from user_message where  user_id=#{userId}") 
    List<UserMessage> selectByUserId(@Param("userId") Integer id);

    
}