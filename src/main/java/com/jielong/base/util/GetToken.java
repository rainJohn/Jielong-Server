package com.jielong.base.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


//CommandLineRunner会在项目启动后自动执行，这里将它设置为只在 prod 环境下执行，是为了在本地单元测试的时候，让它不自动执行
@Profile("prod")
@Component
public class GetToken implements CommandLineRunner{

	private static StringBuilder url=new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential");
	
	public static String accessToken="";	
	


	@Override
	public void run(String... args) throws Exception {
		while(true) {
			netAccessToken();
			Thread.sleep(7000000);
		}
		
		
	}

	public static void netAccessToken() throws JSONException {
		url.append("&appid=").append(Constant.APPID).append("&secret=").append(Constant.SECRET);
		String result=NetworkConnection.get(url.toString());
		JSONObject jsonObject=new JSONObject(result);
		String token=jsonObject.getString("access_token");
		accessToken=token;
		
	}

	public static String getAccessToken() {
		return accessToken;
	}

	
	
}
