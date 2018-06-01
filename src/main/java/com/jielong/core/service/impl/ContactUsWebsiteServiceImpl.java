package com.jielong.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.ContactUsWebsiteMapper;
import com.jielong.core.domain.ContactUsWebsite;
import com.jielong.core.service.ContactUsWebsiteService;

@Service
public class ContactUsWebsiteServiceImpl implements ContactUsWebsiteService {
    @Autowired
    ContactUsWebsiteMapper mapper;
	
	@Override
	public ResponseBean<Integer> insert(ContactUsWebsite contactUsWebsite) {
		
		Integer result=mapper.insertSelective(contactUsWebsite);
		
		return new ResponseBean<Integer>(result);
	}

}
