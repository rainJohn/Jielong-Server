package com.jielong.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
import com.jielong.base.util.Utils;
import com.aliyun.oss.OSSClient;
import com.jielong.base.util.Constants;
import com.jielong.base.util.FileUtils;
import com.jielong.base.util.OSSClientConstants;
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
			  String fileUrl=Constants.UPLOADED_FOLDER +url;
			  
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
	public String downloadOrder(@RequestParam("jielongId")Integer jielongId,HttpServletResponse response) {
		
		XSSFWorkbook wb=fileService.exportOrder(jielongId);
		String path=Constants.SAVED_FOLDER;
		File dirFile=new File(path);
		File file=null;
		String filePath="";
		if (!dirFile.exists()) {
			if (dirFile.mkdirs()) {
				System.out.println("文件夹创建成功！创建后的目录为"+dirFile.getPath());
			}
		}
		
		
			
			try {
				 file = new File(path+"订单详情.xlsx");
				 wb.write(new FileOutputStream(file)); 
				
				 filePath=ossUpload(file);
				 
				 
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return filePath;
		
		} 
	
	public String ossUpload(File file) {
		/*// endpoint以杭州为例，其它region请按实际情况填写
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号
		String accessKeyId = "<yourAccessKeyId>";
		String accessKeySecret = "<yourAccessKeySecret>";*/
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(OSSClientConstants.ENDPOINT, OSSClientConstants.ACCESS_KEY_ID, OSSClientConstants.ACCESS_KEY_SECRET);
		// 上传文件
		String fileName="savedOrder/"+Utils.createFileName()+".xlsx";
		ossClient.putObject(OSSClientConstants.BUCKET_NAME, fileName, file);
		// 关闭client
		ossClient.shutdown();
		return fileName;
	}
		
	
    	

}
