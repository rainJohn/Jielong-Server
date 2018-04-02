package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.UserMessageService;

@RestController
@RequestMapping("/userMessage")
public class UserMessageController {
	@Autowired
	UserMessageService userMessageService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody UserMessage userMessage){
		return userMessageService.insert(userMessage);
	}
	
	@RequestMapping("/update")
	public ResponseBean<Integer> update(@RequestBody UserMessage userMessage){
		return userMessageService.update(userMessage);
	}
	
	@RequestMapping("/selectAll")
	public ResponseBean<List<UserMessage>> selectAll(){
		return userMessageService.selectAll();
	}
	
	@RequestMapping("/deleteById")
	public ResponseBean<Integer> deleteById(@RequestParam("id") Integer id){
		return userMessageService.delete(id);
	}
	@RequestMapping("/updateReadStatus")
	public ResponseBean<Integer> updateReadStatus(@RequestParam("id") Integer id){
		return userMessageService.updateReadeState(id);
	}
	

}
