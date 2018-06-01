package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.ContactUsWebsite;

public interface ContactUsWebsiteService {

	ResponseBean<Integer> insert(ContactUsWebsite contactUsWebsite);
	
}
