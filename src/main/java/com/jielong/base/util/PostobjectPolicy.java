package com.jielong.base.util;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import sun.misc.BASE64Encoder;
import com.aliyun.oss.common.utils.BinaryUtil;



/**
 * 生成post回调策略
 * 
 * */
public class PostobjectPolicy {

	private static Logger logger = Logger.getLogger(PostobjectPolicy.class);
	
	private static final long serialVersionUID = 5522372203700422672L;
	/**
	 * 将生成的object 直接返回给前端
	 * */
	public static JSONObject createPolicy(String dir){
		OSSClient ossClient = new OSSClient(OSSClientConstants.ENDPOINT,OSSClientConstants.ACCESS_KEY_ID,OSSClientConstants.ACCESS_KEY_SECRET);
		long expireTime = 50000;
		long expireEndTime = System.currentTimeMillis()+expireTime*1000;
		Date expiration = new Date(expireEndTime);
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
		
		String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
		String encodedPolicy;
		String postSignature;
		
		try{
			byte[] binaryData = postPolicy.getBytes("utf-8");
			encodedPolicy = BinaryUtil.toBase64String(binaryData);
			postSignature = ossClient.calculatePostSignature(postPolicy);
		}catch(Exception e){
			logger.error(e);
			return null;
		}
		Map<String,Object> respMap = new LinkedHashMap<String,Object>();
		respMap.put("accessKeyId", OSSClientConstants.ACCESS_KEY_ID);
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", dir);
		respMap.put("host",OSSClientConstants.HOST);
		respMap.put("expire", String.valueOf(expireEndTime/1000));
		
		JSONObject callback = new JSONObject();
		callback.put("callbackUrl", "127.0.0.1:8081/oss/callback");
		callback.put("callbackHost", "oss-cn-shanghai.aliyuncs.com");
		callback.put("callbackBody", "{\"mimeType\":${mimeType},\"size\":${size}}");
		callback.put("callbackBodyType","application/json");
		
		BASE64Encoder encoder = new BASE64Encoder();
		respMap.put("callback", encoder.encode(callback.toString().getBytes()));
		
		return new JSONObject(respMap);
	}
	
}
