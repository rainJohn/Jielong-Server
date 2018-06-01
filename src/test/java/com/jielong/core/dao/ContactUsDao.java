package com.jielong.core.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jielong.core.domain.ContactUsWebsite;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactUsDao {

	@Autowired
	ContactUsMapper contactUsMapper;
	
	@Autowired
	ContactUsWebsiteMapper contactUsWebsiteMapper;
	
	
	@Test
	public void insertTest() {
		
		ContactUsWebsite contactUsWebsite=new ContactUsWebsite();
		contactUsWebsite.setName("曹星昱");
		contactUsWebsite.setPhone("111111110000");
		contactUsWebsite.setWechat("1746569077");
		contactUsWebsite.setDetail("怎么用????????");
		
		int result=contactUsWebsiteMapper.insertSelective(contactUsWebsite);
		
		Assert.assertEquals((int)1, result);
		
		
	}
	
	
}
