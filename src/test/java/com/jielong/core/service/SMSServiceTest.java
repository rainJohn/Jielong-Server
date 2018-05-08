package com.jielong.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.jielong.Application;
import com.jielong.core.beans.ResponseBean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SMSServiceTest {

	@Autowired
	SmsService smsService;

	@Test
	public void sendSMStest() {

		String phoneNumber = "17621503621";
		String templateCode = "SMS_111715045";
       // System.out.println(123);
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
       // System.out.println(123);
		ResponseBean<String> responseBean = smsService.sendVerCodeSMS(phoneNumber, templateCode);
		if (responseBean.getErrorCode() == 0) {
			System.out.println("验证码发送成功，验证码是："+responseBean.getData());
		} else {
			System.out.println(responseBean.getErrorMessage());
		}
	}
	

}
