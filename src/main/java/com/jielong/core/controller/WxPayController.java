package com.jielong.core.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.service.impl.WxPayServiceImpl;

@RestController
@RequestMapping("/wxpay")
public class WxPayController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WxPayController.class);
	
	@Autowired
	WxPayServiceImpl wxPayService;
	
   //异步接收通知	
   @RequestMapping("/notify")
   public void notify(HttpServletRequest request, HttpServletResponse response) {
	   LOGGER.info("微信支付异步接收到通知");
	   Map<String, String[]> map=request.getParameterMap();
	   map.forEach((k,v)->{
		   LOGGER.info(k+"————>"+v);     
		   
	   });
	   
   }
   
   @RequestMapping("/pay")
   public ResponseBean<Map<String, String>> pay() {
	  // String ip=request.getRemoteAddr();
	   String ip="47.88.54.113";
	   LOGGER.info("终端ip地址："+ip);
	   
	   String orderNum="12321212122121212";
	   String openId="oQ_fh5FbPEail4QKnFnVYgDOSc14";
	   
	   ResponseBean<Map<String, String>> responseBean=new ResponseBean<>();
	   //支付1分钱
	   Map<String, String>  map=wxPayService.wxPay(openId,null, orderNum,1,ip);
	   if (null!=map) {
		 responseBean.setData(map);
	  }else {
		  responseBean.setErrorCode(ErrorCode.PAY_EXCEPTION);
		  responseBean.setErrorMessage("支付发生错误");
	  }
	   
	   return responseBean;
	   
   }
	
}
