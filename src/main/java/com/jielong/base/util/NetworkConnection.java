package com.jielong.base.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 访问网络工具类
 * @author cxy
 *
 */
public class NetworkConnection {

    static Logger logger=LoggerFactory.getLogger(NetworkConnection.class);
	/**
	 * GET方式
	 * @param strUrl
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String get(String strUrl) {
		String result = "error";

		try {
            System.out.println(strUrl); 
			// 建立连接
			URL url = new URL(strUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

			// 设置HttpURLConnection的属性
			httpConn.setRequestMethod("GET");
			httpConn.setReadTimeout(5000);
			httpConn.setConnectTimeout(5000);

			// 只是建立一个连接, 并不会发送真正http请求 (可以不调用)
			httpConn.connect();

			// 通过响应码来判断是否连接成功
			if (httpConn.getResponseCode() == 200) {
				// 获得服务器返回的字节流
				InputStream is = httpConn.getInputStream();

				// 内存输出流,适合数据量比较小的字符串 和 图片
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				// 可使用 toByteArray() 和 toString() 获取数据。
				byte[] response = baos.toByteArray();
				result = new String(response);
				System.out.println(result);
				is.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			return result;
		
		}

	}
	
	/**
	 * post 方法
	 * @param url
	 * @param reqBody
	 * @return
	 * @throws Exception
	 */
	public static String post(String url,String reqBody) throws Exception{
		    String UTF8 = "UTF-8";
	     //   String reqBody = "<xml><body>测试商家-商品类目</body><trade_type>NATIVE</trade_type><mch_id>11473623</mch_id><sign_type>HMAC-SHA256</sign_type><nonce_str>b1089cb0231011e7b7e1484520356fdc</nonce_str><detail /><fee_type>CNY</fee_type><device_info>WEB</device_info><out_trade_no>20161909105959000000111108</out_trade_no><total_fee>1</total_fee><appid>wxab8acb865bb1637e</appid><notify_url>http://test.letiantian.com/wxpay/notify</notify_url><sign>78F24E555374B988277D18633BF2D4CA23A6EAF06FEE0CF1E50EA4EADEEC41A3</sign><spbill_create_ip>123.12.12.123</spbill_create_ip></xml>";
	        URL httpUrl = new URL(url);
	        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
	        //httpURLConnection.setRequestProperty("Host", "api.mch.weixin.qq.com");
	        httpURLConnection.setDoOutput(true);
	        httpURLConnection.setRequestMethod("POST");
	        httpURLConnection.setConnectTimeout(10*1000);
	        httpURLConnection.setReadTimeout(10*1000);
	        httpURLConnection.connect();
	        OutputStream outputStream = httpURLConnection.getOutputStream();
	        outputStream.write(reqBody.getBytes(UTF8));

	        //获取内容
	        InputStream inputStream = httpURLConnection.getInputStream();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
	        final StringBuffer stringBuffer = new StringBuffer();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            stringBuffer.append(line);
	        }
	        String resp = stringBuffer.toString();
	        if (stringBuffer!=null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (inputStream!=null) {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (outputStream!=null) {
	            try {
	                outputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	      //  System.out.println(resp);
	        return resp;

	}
	
	@SuppressWarnings("finally")
	public static InputStream  getStream(String strUrl,String params) {
		InputStream is=null;
		try {		
			// 建立连接
			URL url = new URL(strUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

			// 设置HttpURLConnection的属性
			httpConn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			 //一定要设置 Content-Type 要不然服务端接收不到参数
			httpConn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
			OutputStream out=httpConn.getOutputStream();
			//输出流里写入post参数
			out.write(params.getBytes());
			out.flush();
            out.close();  
         
			// 通过响应码来判断是否连接成功
			if (httpConn.getResponseCode() == 200) {
				// 获得服务器返回的字节流
			  is = httpConn.getInputStream();

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取二维码流错误");
		} finally {
			return is;
		}
	}

	/*public static  InputStream getImageStream(String url,String params) throws Exception{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		StringEntity entity;
		entity = new StringEntity(params);
		entity.setContentType("image/png");

		httpPost.setEntity(entity);
		HttpResponse response;

		response = httpClient.execute(httpPost);
		InputStream inputStream = response.getEntity().getContent();
		return inputStream;
	}*/




}
