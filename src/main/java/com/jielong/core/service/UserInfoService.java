package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.UserInfo;


public interface UserInfoService {

   ResponseBean<Integer> insert(UserInfo userInfo);
   
   ResponseBean<UserInfo> selectByUserId(Integer userId);
   
   ResponseBean<Integer> update(UserInfo userInfo);
}
