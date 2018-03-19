package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.PageBean;
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
	
	/**
	 * 分页查询
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("/selectByPage")
	public ResponseBean<List<Jielong>> selectByPage(@RequestBody PageBean pageBean){
		return jielongService.selectByPage(pageBean);
	}
	/**
	 * 根据userId查询
	 *
	 */
	@RequestMapping("/selectByUserId")
	public ResponseBean<List<Jielong>> selectByUserId(@RequestParam("userId") Integer userId){
		return jielongService.selectByUserId(userId);
	}
    
    	
}
