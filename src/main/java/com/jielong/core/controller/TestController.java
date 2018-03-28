package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jielong.core.service.GoodsClassService;

@Controller
public class TestController {
	
    @Autowired GoodsClassService goodsClassService;

	@RequestMapping("/test")
	@ResponseBody
    public String test() {
        return "Hello test!";
    }
	
	//返回首页
	@RequestMapping("/home")
	public String home()
	{ 
		return "home";
	  	
	}
	
//	@RequestMapping("/uploadCarousel")
//	public String upload() {
//		return "upload";
//	}
	
	
}
