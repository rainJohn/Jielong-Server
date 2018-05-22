package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.ContactUs;

public interface ContactUsService {
	
  ResponseBean<Integer> insert(ContactUs contactUs);	

}
