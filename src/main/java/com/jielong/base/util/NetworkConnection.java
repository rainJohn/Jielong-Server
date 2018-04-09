package com.jielong.base.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
				System.out.println("客户端执行完毕!!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			return result;
		
		}

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

}
