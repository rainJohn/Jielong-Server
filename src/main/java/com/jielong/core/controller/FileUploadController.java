package com.jielong.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jielong.base.util.FileUpload;
import com.jielong.core.beans.ResponseBean;

@RestController
public class FileUploadController {
	
	@RequestMapping("/uploadImage")
	public ResponseBean<String> uploadImage(@RequestParam("image") MultipartFile file){
		
		return FileUpload.uploadSingleFile(file);
		
	}
    	

}
