package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jielong.base.util.FileUtils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Carousel;
import com.jielong.core.service.CarouselImageService;


@Controller
public class CarouselImageController {

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
	@RequestMapping("/uploadCarousel")
	public ResponseBean<String> uploadCarousel(@RequestParam("carousel") MultipartFile file){
		return FileUtils.uploadSingleFile(file);
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
	 * 删除轮转图
	 * */
	@RequestMapping(value="/deleteCarouselByKey")
	@ResponseBody
	public String deleteCarouselByKey(int id){
		Boolean result = this.carouselImageService.deleteCarouselByKey(id);
		if(result==false){
		return "删除失败!";
		}
		return "删除成功!";
	}
	
	/**
	 * 修改轮转图状态禁用
	 * */
	@RequestMapping(value="/forbiddenCarouselByKey")
	@ResponseBody
	public String forbiddenCarouselByKey(int id){
		Boolean result = this.carouselImageService.forbiddenCarouselByKey(id);
		if(result == false){
			return "禁用失败！";
		}
		return "禁用成功！";
	}
	/**
	 * 启用
	 * */
	@RequestMapping(value="/updateCarouselTypeByKey")
	@ResponseBody
	public String startCarouselByKey(int id){
		Boolean result = this.carouselImageService.startCarouselByKey(id);
		if(result == false){
			return "启用失败！";
		}
		return "启用成功！";
	}
}
	
