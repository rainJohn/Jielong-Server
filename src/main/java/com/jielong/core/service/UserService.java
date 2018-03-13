package com.jielong.core.service;

import java.util.Map;

import com.jielong.core.beans.ResponseBean;


public interface UserService {
	
	ResponseBean<Map<String, Object>> login(String code);
	
	
	

}
