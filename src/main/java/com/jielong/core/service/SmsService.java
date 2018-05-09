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
	/**
	 * 发送通知类短信：可携带多个参数
	 * @param phoneNumber
	 * @param templateCode
	 * @param params 参数
	 * @return
	 */
	ResponseBean<Integer> sendNotificationSMS(String phoneNumber, String templateCode,String params);
}
