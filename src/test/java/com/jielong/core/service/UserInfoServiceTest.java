package com.jielong.core.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.UserInfo;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {
 
	@Autowired
	UserInfoService userInfoService;
	
	/**
	 * 根据条件查询
	 */
	@Test
	public void testSelectByConditions() {
		UserInfo userInfo=new UserInfo();
		userInfo.setName("曹星昱");
		ResponseBean<List<UserInfo>> responseBean=userInfoService.selectByConditions(userInfo);
		 
		List<UserInfo> list=responseBean.getData();
		list.stream().forEach(user->System.out.println(user.getName()));
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testEquals() {
		String str="123";
		if (str.equals("abc")) {
			System.out.println("123");
		}
	}
	
	@Test
	public void testSelectAll() {
		
		List<UserInfo> list=userInfoService.selectAll().getData();
		list.forEach(userInfo->System.out.println(userInfo.getNickName()+" "+userInfo.getState()));
		
	}
}
