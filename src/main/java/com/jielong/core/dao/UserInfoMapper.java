package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.User;
import com.jielong.core.domain.UserInfo;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);
    
    @Select("select * from user_info where user_id = #{userId}")
    List<UserInfo> selectByUserId(@Param("userId")Integer userId);
    
    @Select("select a.*,b.state from user_info a left join user b on a.user_id=b.id")
    List<UserInfo> selectAll();

    //根据userId更新用户二维码url
    @Update("update user_info set qrcode_url = #{qrcodeUrl} where user_id = #{userId}")
    int updateQrcode(@Param("qrcodeUrl") String qrcodeUrl,@Param("userId") Integer userId);
    /**
     * 根据条件查询
     * @param userInfo
     * @return
     */
    List<UserInfo> selectByConditions(UserInfo userInfo);
}