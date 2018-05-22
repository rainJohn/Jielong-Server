package com.jielong.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.ContactUs;
import com.jielong.core.service.ContactUsService;

@RestController
public class OfficialWebsiteController {

	@Autowired
	ContactUsService contactUsService;
	
	@RequestMapping("/contactUs")
	public ResponseBean<Integer> contactUs(@RequestBody ContactUs contactUs){
		return contactUsService.insert(contactUs);
	}
	
	
}
