package com.jielong.core.service;


//微信支付接口
public interface WxPayService {
	
	//1、第一步下单签名
	String paySignature();
	//2、下单
	String unifiedorder();
	//3、支付

}
