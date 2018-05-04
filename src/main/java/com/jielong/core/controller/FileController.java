package com.jielong.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.jielong.base.util.Constant;
import com.jielong.base.util.FileUtils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.service.FileService;

@RestController
public class FileController {
	
	@Autowired
	FileService fileService;
	
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
	
	//下载订单
	@RequestMapping("/downloadOrder")
	public void downloadOrder(@RequestParam("jielongId")Integer jielongId,HttpServletResponse response) {
		
		XSSFWorkbook wb=fileService.exportOrder(jielongId);
		String path=Constant.SAVED_FOLDER;
		File dirFile=new File(path);
		if (!dirFile.exists()) {
			if (dirFile.mkdirs()) {
				System.out.println("文件夹创建成功！创建后的目录为"+dirFile.getPath());
			}
		}
		
		
			//FileOutputStream fos=new FileOutputStream(salaryList.get(0).getPayDate()+salaryList.get(0).getDepName()+"工资明细表");
			try {
				 File file = new File(path+"订单详情.xlsx");
				 wb.write(new FileOutputStream(file)); 
				
				 OutputStream output=response.getOutputStream();
				 response.reset();
				 response.setContentType("application/octet-stream");  
				 response.setCharacterEncoding("UTF-8");  
				 String fileName = new String(("订单详情表").getBytes("UTF-8"),"ISO-8859-1");
				    
				 response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xlsx");
				 FileInputStream inputStream = new FileInputStream(file);
				 int length = 0;
				 byte[] buf = new byte[1024];
				 while ((length = inputStream.read(buf)) > 0) {	            	
				    	    output.write(buf, 0, length);
				    	    
				     }
				    inputStream.close();
				    output.flush();
				    output.close();
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} 
		
	
    	

}
