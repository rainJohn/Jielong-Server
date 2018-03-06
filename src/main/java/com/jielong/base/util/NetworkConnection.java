package com.jielong.base.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 访问网络工具类
 * @author cxy
 *
 */
public class NetworkConnection {


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

}
