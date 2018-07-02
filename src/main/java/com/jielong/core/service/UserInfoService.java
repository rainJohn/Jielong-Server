package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.User;
import com.jielong.core.domain.UserInfo;
import com.mysql.fabric.Response;


public interface UserInfoService {

   ResponseBean<Integer> insert(UserInfo userInfo);
   
   ResponseBean<UserInfo> selectByUserId(Integer userId);
   
   ResponseBean<Integer> update(UserInfo userInfo);
   
   ResponseBean<List<UserInfo>> selectAll();
   
   ResponseBean<List<UserInfo>> selectByConditions(UserInfo userInfo);

   /**
    * 查询该用户的专属二维码url
    * @param userId
    * @return
    */
   ResponseBean<String> selectQRcodeUrl(Integer userId);
}
