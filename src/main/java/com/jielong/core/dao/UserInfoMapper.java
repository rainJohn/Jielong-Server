package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.User;
import com.jielong.core.domain.UserInfo;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    @Select("select * from user_info where user_id = #{userId}")
    List<UserInfo> selectByUserId(@Param("userId")Integer userId);
    
    @Select("select a.*,b.state from user_info a left join user b on a.user_id=b.id")
    List<UserInfo> selectAll();
    
    /**
     * 根据条件查询
     * @param userInfo
     * @return
     */
    List<UserInfo> selectByConditions(UserInfo userInfo);
}