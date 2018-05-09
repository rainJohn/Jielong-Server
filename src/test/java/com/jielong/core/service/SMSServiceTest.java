package com.jielong.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.jielong.core.beans.ResponseBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSServiceTest {

	@Autowired
	SmsService smsService;

	/**
	 * 发送国内短信验证码
	 */
	@Test
	public void sendSMStest() {

		String phoneNumber = "17621503621";
		String templateCode = "SMS_111715045";
      
		ResponseBean<String> responseBean = smsService.sendVerCodeSMS(phoneNumber, templateCode);
		if (responseBean.getErrorCode() == 0) {
			System.out.println("验证码发送成功，验证码是："+responseBean.getData());
		} else {
			System.out.println(responseBean.getErrorMessage());
		}

	}
	
	/**
	 * 发送 国际（港澳台）验证码
	 */
	@Test
	public void sendInternalSMS() {
		
		String phoneNumber = "0016043655388";
		String templateCode = "SMS_134320157";
      
		ResponseBean<String> responseBean = smsService.sendVerCodeSMS(phoneNumber, templateCode);
		if (responseBean.getErrorCode() == 0) {
			System.out.println("验证码发送成功，验证码是："+responseBean.getData());
		} else {
			System.out.println(responseBean.getErrorMessage());
		}
	}
	
	/**
	 * 发送国际版 通知类短信
	 */
	@Test
	public void testSendInternalNotificationSMS() {
		String phoneNumber = "0016043655388";
		String templateCode = "SMS_134320860";
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("name", "Mary");
		jsonObject.put("appname", "mart Application");
		
      ResponseBean<Integer> responseBean=smsService.sendNotificationSMS(phoneNumber, templateCode, jsonObject.toJSONString());
      
       if (responseBean.getErrorCode() == 0) {
			System.out.println("验证码发送成功，发送数量："+responseBean.getData());
		} else {
			System.out.println(responseBean.getErrorMessage());
		}
		
		
	}
	

}
