package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

 

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    @Select("select * from user where open_id = #{openId}")
    List<User> selectByOpenId(@Param("openId") String sessionId);
    
    //查询所有的用户id
    @Select("select id from user")
    List<Integer> selectAllId();
}