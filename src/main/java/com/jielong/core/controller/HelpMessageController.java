package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.HelpMessage;
import com.jielong.core.service.HelpMessageService;

@RestController
@RequestMapping("/helpMessage")
public class HelpMessageController {
	
	@Autowired
	HelpMessageService helpMessageService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody HelpMessage helpMessage){
		
		return helpMessageService.insert(helpMessage);
		
	}
	
	
	@RequestMapping("/update")
	public ResponseBean<Integer> update(@RequestBody HelpMessage helpMessage){
		return helpMessageService.update(helpMessage);
	}
	
	@RequestMapping("/selectAll")
	public ResponseBean<List<HelpMessage>> selectAll(){
		
		return helpMessageService.selectAll();
	} 
	
	@RequestMapping("/delete")
	public ResponseBean<Integer> delete(@RequestParam("id") Integer id){
		return helpMessageService.deleteById(id);
	}
	
	

}
