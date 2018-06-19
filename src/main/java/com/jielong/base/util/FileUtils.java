package com.jielong.base.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import com.jielong.core.beans.ResponseBean;

/**
 * 上传文件类
 * @author cxy
 *
 */
public class FileUtils {
	
	/**
	 * 上传单个文件
	 * @param file
	 */
	public static ResponseBean<String> uploadSingleFile(MultipartFile file) {
		ResponseBean<String> responseBean=new ResponseBean<String>();
		if (file.isEmpty()) {
          responseBean.setErrorCode(ErrorCode.FILE_UPLOAD_EXCEPTION);
          responseBean.setErrorMessage("上传文件为空");
        }

        try {
          
            byte[] bytes = file.getBytes();
            
            String[] originalName=file.getOriginalFilename().split("\\.");
            //文件后缀名
            String fileSuffix=originalName[originalName.length-1];
            String fileName=Utils.createFileName() +"."+fileSuffix;
            File parentFile=new File(Constants.UPLOADED_FOLDER);
            if (!(parentFile.exists()&& parentFile.isDirectory())) {
				parentFile.mkdirs();
			}
            Path path = Paths.get(Constants.UPLOADED_FOLDER +fileName);
            
            Files.write(path, bytes);
            responseBean.setData("/getImage/"+fileName);
            
            System.out.println("上传文件成功！");

          
        } catch (IOException e) {
            e.printStackTrace();
            responseBean.setErrorCode(ErrorCode.FILE_UPLOAD_EXCEPTION);
            responseBean.setErrorMessage("上传文件错误");
            System.out.println("上传文件错误！");
        }
        
        return responseBean;

      
	}
	
	/** 
     * 获取URL图片流 
     * @param urlString 
     * @return 
     */  
    public static InputStream createPic(String urlString){  
        InputStream is = null;  
        try {  
            // 构造URL  
            URL url = new URL(urlString);  
            // 打开连接  
            URLConnection con = url.openConnection();  
            // 输入流  
            is = con.getInputStream();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return is;  
    }  

}
