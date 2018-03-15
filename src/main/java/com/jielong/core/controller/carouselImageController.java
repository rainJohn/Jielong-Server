package com.jielong.core.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.jielong.base.util.Constant;
import com.jielong.core.domain.Carousel;
import com.jielong.core.service.CarouselImageService;


@Controller
public class carouselImageController {

	@Autowired
	CarouselImageService carouselImageService;
	
	@RequestMapping(value="/carouselImage",method=RequestMethod.GET)
	public String carouselImage(){
		return "carouselImage";
	}
	
	/**
	 * 上传文件方法
	 * @param file 前台上传的文件对象
	 * @return
	 * */
	@RequestMapping(value="/uploadCarousel",method=RequestMethod.POST)
	@ResponseBody
	public String upLoad(HttpServletRequest request,MultipartFile file){
		try{
			   //上传目录地址
			   String uploadDir = Constant.UPLOADED_FOLDER;
			   System.out.println(uploadDir);
			   //如果目录不存在，自动创建文件夹
			   File dir = new File(uploadDir);
			   if(!dir.exists()){
				   dir.mkdir();
			   }
			   //上传文件名
			   String filename = file.getOriginalFilename();
			   //服务器端保存的文件对象
			   File serverFile = new File(uploadDir + filename);
			   //将上传的文件写入到服务器端文件内
			   file.transferTo(serverFile);
			   
			   Carousel carousel = new Carousel();
			   carousel.setCarouselAddress(uploadDir+filename);
			   carouselImageService.insert(carousel);
		   }catch(Exception e){
			   //打印错误信息
			   return "上传失败";
		   }
		   return "上传成功";
	   }
	

}
	
