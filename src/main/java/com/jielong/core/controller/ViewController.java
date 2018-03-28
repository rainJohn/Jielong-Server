package com.jielong.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/home")
	public String home()
	{ 
		return "home";
	  	
	}
	@RequestMapping("/login")
	public String login()
	{ 
		return "login";
	  	
	}
	@RequestMapping("/loginError")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
	    return "login";
	}

	
}
