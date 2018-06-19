package com.jielong.base.util;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jielong.base.util.Constants.SignType;
import com.jielong.core.service.impl.WxPayServiceImpl;
import com.mysql.jdbc.StringUtils;

public class WxPayUtilsTest {
	
	
		
	@Test
	public void prePayTest() {
		WxPayServiceImpl wxPayService=new WxPayServiceImpl();
		String orderNum="12321212122121212";
		String openId="oQ_fh5FbPEail4QKnFnVYgDOSc14";
		wxPayService.wxPay(openId,null, orderNum,100,"47.88.54.113");
	}
	
	
	//支付请求
	public void testPaySignature() {
		
		
	}

}
