package com.jielong.core.service.impl;

import java.io.*;

import com.jielong.base.util.Constants;
import com.jielong.base.util.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jielong.base.util.GetToken;
import com.jielong.base.util.NetworkConnection;
import com.jielong.core.service.QRcodeService;

@Service
public class QRcodeServiceImpl implements QRcodeService{
	
	private String url="https://api.weixin.qq.com/wxa/getwxacode?access_token=" ;

	private  static  final Logger LOGGER=LoggerFactory.getLogger(QRcodeServiceImpl.class);
    
	@Override
	public InputStream getCodeStream(Integer id)  {
        String accessToken=GetToken.getAccessToken();
        InputStream inputStream=null;
		try {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("path", "pages/detail/detail?id="+id+"&fromMine=0");
			
			inputStream = NetworkConnection.getStream(url+accessToken, jsonObject.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return inputStream;
	}

	/**
	 * 生成二维码图片
	 * @param path 扫描二维码跳转到的路径 可在?后加参数
	 * @return
	 */
	public File getQRcodeImage(String path){
		//String accessToken=GetToken.getAccessToken();
		InputStream is=null;
		OutputStream out=null;
		File imageFile=null;
		try {
			String accessToken=Utils.getTestToken();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("path", path);
			//jsonObject.put("width",430);
			is = NetworkConnection.getStream(url+accessToken, jsonObject.toString());
			if (is!=null){
				//保存为jpg格式图片文件
				String fileName="qrcode.png";
				String filePath=Constants.SAVED_FOLDER+fileName;
				imageFile=new File(filePath);
				if (!imageFile.exists()){
					imageFile.createNewFile();
				}
				out=new FileOutputStream(imageFile);
				byte[] b = new byte[1024];
				int bytesRead=0;
				while ((bytesRead=is.read(b,0,1024))!=-1) {
					out.write(b,0,bytesRead);// 写入数据
				}
				out.flush();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("生成二维码图片发生错误");

		}finally {
			if (is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return  imageFile;
		}
	}
}
