package com.jielong.core.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jielong.base.util.FileUtils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Carousel;
import com.jielong.core.service.CarouselImageService;


@Controller
public class CarouselImageController {

	@Autowired
	CarouselImageService carouselImageService;

	
	
	/**
	 * 上传文件方法
	 * @param file 前台上传的文件对象
	 * @return
	 * */
	@RequestMapping("/uploadCarousel")
	@ResponseBody
	public ResponseBean<Integer> uploadCarousel(@RequestParam("carousel") MultipartFile file){
		 FileUtils.uploadSingleFile(file);
		 Carousel carousel = new Carousel();
		 if(FileUtils.uploadSingleFile(file).getErrorCode() == 0){
		 carousel.setCarouseladdress(FileUtils.uploadSingleFile(file).getData());
		 carousel.setType("0");
		 }
		 return this.carouselImageService.insert(carousel);
	}
//	@RequestMapping(value="/uploadCarousel",method=RequestMethod.POST)
//	@ResponseBody
//	public String upLoad(HttpServletRequest request,MultipartFile file){
//		try{
//			   //上传目录地址
//			   String uploadDir = Constant.UPLOADED_FOLDER;
//			   System.out.println(uploadDir);
//			   //如果目录不存在，自动创建文件夹
//			   File dir = new File(uploadDir);
//			   if(!dir.exists()){
//				   dir.mkdir();
//			   }
//			   //上传文件名
//			   String filename = file.getOriginalFilename();
//			   //服务器端保存的文件对象
//			   File serverFile = new File(uploadDir + filename);
//			   //将上传的文件写入到服务器端文件内
//			   file.transferTo(serverFile);
//			   
//			   Carousel carousel = new Carousel();
//			   carousel.setCarouseladdress(uploadDir+filename);
//			   carouselImageService.insert(carousel);
//		   }catch(Exception e){
//			   //打印错误信息
//			   return "上传失败";
//		   }
//		   return "上传成功";
//	   }
	

	
	/**
	 * 查询当前所有轮转图
	 * */
	@RequestMapping(value="/queryCarousels",method=RequestMethod.GET)
	@ResponseBody
	public List<Carousel> queryCarouselList(){
		List<Carousel> carouselList = this.carouselImageService.queryCarousels();
		return carouselList;
	}
	
	/**
	 * 查询启用状态轮播图
	 * */
	@RequestMapping(value="/queryStartCarousels")
	@ResponseBody
	public List<Carousel> queryStartCarousels(){
		List<Carousel> startCarousels = this.carouselImageService.queryStartCarousels();
		return startCarousels;
	}
	
	/**
	 * 删除轮转图
	 * */
	@RequestMapping(value="/deleteCarouselByKey")
	@ResponseBody
	public ResponseBean<Integer> deleteCarouselByKey(int id){
		return this.carouselImageService.deleteCarouselByKey(id);
	}
	
	/**
	 * 修改轮转图状态禁用
	 * */
	@RequestMapping(value="/forbiddenCarouselByKey")
	@ResponseBody
	public ResponseBean<Integer> forbiddenCarouselByKey(int id){
		return this.carouselImageService.forbiddenCarouselByKey(id);
	}
	/**
	 * 启用
	 * */
	@RequestMapping(value="/startCarouselByKey")
	@ResponseBody
	public ResponseBean<Integer> startCarouselByKey(int id){
		return this.carouselImageService.startCarouselByKey(id);
	}
	
	/**
	 * 添加备注
	 * */
	@RequestMapping(value="/addRemarkById")
	@ResponseBody
	public ResponseBean<Integer> addRemarkById(int id,String remark){
		return this.carouselImageService.addRemarkById(id,remark);
	}
}
	
