package com.jielong.core.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
   @Autowired
   UserService userService;
	
  	
   @RequestMapping("/login") 	
   public ResponseBean<Map<String, Object>>	getSessionKey(@RequestParam("code") String code){
	  
	  return userService.login(code);    
	     
   }

}
