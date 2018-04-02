package com.jielong.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewController {

	/** 
	 * @return 首页
	 */
	@RequestMapping("/home")
	public String home()
	{ 
		return "home";
	  	
	}
	/**
	 * 
	 * @return 轮播图
	 */
	@RequestMapping(value="/view/carouselImage")
	public String carouselImage(){
		return "carouselImage";
	}
	
	
	/**
	 * 
	 * @return 登录页
	 */
	@RequestMapping("/login")
	public String login()
	{ 
		return "login";
	  	
	}
	
	/**
	 * 登录错误，重新返回登录页
	 * @param model
	 * @return   
	 */
	@RequestMapping("/loginError")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
	    return "login";
	}

	
}
