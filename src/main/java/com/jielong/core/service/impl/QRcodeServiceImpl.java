package com.jielong.core.service.impl;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.jielong.base.util.GetToken;
import com.jielong.base.util.NetworkConnection;
import com.jielong.core.service.QRcodeService;

@Service
public class QRcodeServiceImpl implements QRcodeService{
	
	private String url="https://api.weixin.qq.com/wxa/getwxacode?access_token=" ;
    
	@Override
	public InputStream getCodeStream(Integer id)  {
        String accessToken=GetToken.getAccessToken();
      //String params="path=pages/detail/detail?jielongId="+id;
        InputStream inputStream=null;
		try {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("path", "pages/detail/detail?jielongId="+id);
			
			inputStream = NetworkConnection.getStream(url+accessToken, jsonObject.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return inputStream;
	}
}
