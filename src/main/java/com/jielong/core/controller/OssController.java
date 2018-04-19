package com.jielong.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jielong.base.util.CallbackUtil;
import com.jielong.base.util.PostobjectPolicy;

@RestController
public class OssController {

	
//	/**
//	 * 提供给oss服务器回调的地址
//	 * */
//	@RequestMapping(value = "oss/callback", method = {RequestMethod.POST,RequestMethod.GET})
//	public String callback(@RequestBody String ossCallbackBody,
//			@RequestHeader("Authorization") String authorization,
//            @RequestParam("callback") String callbackMethodName,
//            @RequestHeader("x-oss-pub-key-url") String publicKeyUrlBase64,
//            HttpServletRequest request,
//            HttpServletResponse response){
//		
//		boolean isOssCallback = CallbackUtil.verifyOSSCallbackRequest(authorization, 
//																		publicKeyUrlBase64, 
//																		ossCallbackBody, 
//																		request.getQueryString(), 
//																		request.getRequestURI());
//		
//		if(isOssCallback){
//			response.setStatus(HttpServletResponse.SC_OK);
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("success", true);
//			return jsonObject.toString();
//		}else{
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("success", false);
//			return jsonObject.toString();
//		}
//		
//	}
//	
	
	/**
	 * 客户端调用该请求，应用服务器生成plicy 和 callback 返回至客户端。
	 * */
	@RequestMapping(value = "/oss/policy")
    public String createPolicy(){
        return PostobjectPolicy.createPolicy("upload/").toJSONString();

    }
	
}
