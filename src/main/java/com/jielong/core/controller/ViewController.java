package com.jielong.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	@RequestMapping("/view/home")
	public String home()
	{ 
		return "home";
	  	
	}
	@RequestMapping("/login")
	public String login()
	{ 
		return "login";
	  	
	}
	@RequestMapping("/logout")
	public String logout()
	{ 
		return "login";
	  	
	}
	
}
