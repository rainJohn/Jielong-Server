package com.jielong.core.service;

import java.util.Map;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.User;


public interface UserService {
	
	ResponseBean<Map<String, Object>> login(String code);
	
	 /**
	   * 更新用户状态  post
	   * @param user 需携带两个参数 id(用户的id)、 state(被拉黑时为0，可用为1) 
	   * @return
	   */
	ResponseBean<Integer> updateState(User user);
	
	User selectById(Integer id);
	
	

}
