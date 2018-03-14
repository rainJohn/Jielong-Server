package com.jielong.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Jielong;
import com.jielong.core.service.JielongService;

@RestController
@RequestMapping("/jielong")
public class JielongController {

	@Autowired
	JielongService jielongService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody Jielong jielong){
		return  jielongService.insert(jielong);
	}
    
    	
}
