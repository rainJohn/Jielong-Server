package com.jielong.core.controller;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.jielong.core.service.QRcodeService;

@RestController
public class QRcodeConrtroller {
  
	@Autowired
	QRcodeService qRService;
	
	@RequestMapping("/getQRcode/{jielongId}")
	public void getQRcode(@PathVariable("jielongId") Integer id,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		 
		  try {
			 
			  System.out.println(123);
			  InputStream is = qRService.getCodeStream(id);			  
			  httpServletResponse.setContentType("image/jpg");		 
			  
			  if (is!=null) {
				  OutputStream os = httpServletResponse.getOutputStream();
				  int length = 0;
				  byte[] buf = new byte[1024];
				  
				  while ((length = is.read(buf)) > 0) {	            	
					  os.write(buf, 0, length);
				  	    
				  }
				  os.flush();
				  os.close();				
			} 		
			 
			  
		} catch (Exception e) {
			e.printStackTrace();
		    System.out.println("下载文件错误！");
		}
		 
                  		
	}
	
	
	
}
