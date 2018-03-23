package com.jielong.base.util;

import java.io.IOException;
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
public class FileUpload {
	
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
            Path path = Paths.get(Constant.UPLOADED_FOLDER +Utils.createFileName() +"."+fileSuffix);
            
            Files.write(path, bytes);
            responseBean.setData("上传文件成功！");
            System.out.println("上传文件成功！");

          
        } catch (IOException e) {
            e.printStackTrace();
            responseBean.setErrorCode(ErrorCode.FILE_UPLOAD_EXCEPTION);
            responseBean.setErrorMessage("上传文件错误");
            System.out.println("上传文件错误！");
        }
        
        return responseBean;

      
	}

}
