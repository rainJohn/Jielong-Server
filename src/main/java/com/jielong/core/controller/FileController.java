package com.jielong.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jielong.base.util.Constant;
import com.jielong.base.util.FileUtils;
import com.jielong.core.beans.ResponseBean;

@RestController
public class FileController {
	
	@RequestMapping("/uploadImage")
	public ResponseBean<String> uploadImage(@RequestParam("image") MultipartFile file){
		
		return FileUtils.uploadSingleFile(file);
		
	}
	
	@RequestMapping(value="/getImage/{url}",method = RequestMethod.GET)
	public void getFile(@PathVariable String url,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		 
		  try {
			  String fileUrl=Constant.UPLOADED_FOLDER +url;
			  
			  String[] tempArray=fileUrl.split("\\.");
			  String fileType=tempArray[tempArray.length-1];
			  
			  FileInputStream is = new FileInputStream(new File(fileUrl));
			  
			  httpServletResponse.setContentType("image/"+fileType);
			  OutputStream os = httpServletResponse.getOutputStream();
			  int length = 0;
			  byte[] buf = new byte[1024];
			  while ((length = is.read(buf)) > 0) {	            	
				  os.write(buf, 0, length);
			  	    
			  }
			
			  os.flush();
			  os.close();
			 
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    System.out.println("下载文件错误！");
		}
		 
                  		
	}
    	

}
