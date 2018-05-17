package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.UserInfo;
import com.jielong.core.service.UserInfoService;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

	@Autowired
	UserInfoService userInfoService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody UserInfo userInfo){
		return userInfoService.insert(userInfo);
	}
	
	@RequestMapping("/selectAll")
	public ResponseBean<List<UserInfo>> selectAll(){
		return userInfoService.selectAll();
	}
	
	@RequestMapping("/update")
	public ResponseBean<Integer> update(@RequestBody UserInfo userInfo){
		return userInfoService.update(userInfo);
	}
	
	@RequestMapping("/selectByUserId")
	public ResponseBean<UserInfo> selectByUserId(@RequestParam("userId") Integer userId){
		return userInfoService.selectByUserId(userId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
