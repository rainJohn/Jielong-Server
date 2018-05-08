package com.jielong.core.service;

import com.jielong.core.beans.ResponseBean;

/**
 * 发送短信接口
 * @author cxy
 *
 */
public interface SmsService {
	
   /**
    * 发送验证码类短信	
    * @param phoneNumber 要发送的手机号
    * @param templateCode 短信模板ID
    * @return
    */
	ResponseBean<String> sendVerCodeSMS(String phoneNumber,String templateCode);
}
