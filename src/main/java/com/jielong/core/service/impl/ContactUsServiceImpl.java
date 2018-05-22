package com.jielong.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.ContactUsMapper;
import com.jielong.core.domain.ContactUs;
import com.jielong.core.service.ContactUsService;

@Service
public class ContactUsServiceImpl implements ContactUsService {

	@Autowired
	ContactUsMapper mContactUsMapperapper;
	@Override
	public ResponseBean<Integer> insert(ContactUs contactUs) {
       Integer result=mContactUsMapperapper.insertSelective(contactUs);
       return new ResponseBean<>(result);
    
	}

}
