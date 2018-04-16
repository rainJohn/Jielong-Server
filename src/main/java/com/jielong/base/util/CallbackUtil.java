package com.jielong.base.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import javax.websocket.Decoder.Binary;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.aliyun.oss.common.utils.BinaryUtil;
import com.github.pagehelper.util.StringUtil;

public class CallbackUtil {

	/**
	 * 检验是否是oss服务器发送过来的回调请求
	 * @param authorizationInput base64编码后的地址
	 * @param punkeyInput base64编码后的公钥地址
	 * @param requestBody 请求体
	 * @param queryString 请求查询的参数
	 * @param url 请求的url
	 * @return 校验成功返回true，否则false.
	 * */
	public static final boolean verifyOSSCallbackRequest(String authorizationInput,
														String pubkeyInput,
														String requestBody,
														String queryString,
														String url){
		byte[] publicKeyBytes = BinaryUtil.fromBase64String(pubkeyInput);
		String publicKeyAddr = new String(publicKeyBytes);
		
		String publicKey = executeGet(publicKeyAddr);
		
		if(StringUtil.isEmpty(publicKey)){
			return false;
		}
		publicKey = publicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKey = publicKey.replace("-----END PUBLIC KEY-----", "");
        
        StringBuffer requestContent = new StringBuffer();
        
        requestContent.append(url).append("?").append(queryString).append("\n").append(requestBody);
        
        byte[] sign = BinaryUtil.fromBase64String(authorizationInput);
        
        return doCheck(requestContent.toString(),sign,publicKey);
	}
	
	
	/**
	 * 向OSS服务器发起请求，获取结果用于检查是否是服务器发起的callback请求
	 * @param url oss服务器路径
	 * */
	private static final String executeGet(String url){
		BufferedReader in = null;
		
		String content = null;
		
		try{
			//定义HttpClient
			@SuppressWarnings("resource")
			DefaultHttpClient client = new DefaultHttpClient();
			//实例化HttpClient
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while((line = in.readLine())!=null){
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		}catch(Exception e){
			return null;
		}finally {
			if(in != null){
				try{
					in.close();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			return content;
		}
	}
	
	/**
	 * 检查公钥和数字签名正确
	 * 
	 * @param  content  	需要检查的内容
	 * @param  sign 		数字签名
	 * @param  publicKey	公钥
	 * 
	 * */
	public static final boolean doCheck(String content,byte[] sign,String publicKey){
		try{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			boolean bverify = signature.verify(sign);
			return bverify;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}
