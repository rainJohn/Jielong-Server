package com.jielong.core.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.ContactUs;
import com.jielong.core.domain.ContactUsWebsite;
import com.jielong.core.service.ContactUsService;
import com.jielong.core.service.ContactUsWebsiteService;


@RestController
@CrossOrigin
public class OfficialWebsiteController {

	@Autowired
	ContactUsService contactUsService;
	@Autowired
	ContactUsWebsiteService contactUsWebsiteService;
	
	@RequestMapping("/contactUs")
	public ResponseBean<Integer> contactUs(@RequestBody ContactUs contactUs){
		return contactUsService.insert(contactUs);
	}
	
	
	@RequestMapping("/website/contactUs")
	public ResponseBean<Integer> contactUsWebsite(@RequestBody ContactUsWebsite contactUsWebsite){
		 return contactUsWebsiteService.insert(contactUsWebsite);
		
	}
	
	
}
