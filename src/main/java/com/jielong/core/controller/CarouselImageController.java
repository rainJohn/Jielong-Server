package com.jielong.core.controller;

import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.jielong.base.util.ErrorCode;
import com.jielong.base.util.OSSClientConstants;
import com.jielong.base.util.Utils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Carousel;
import com.jielong.core.service.CarouselImageService;

@Controller
public class CarouselImageController {

	@Autowired
	CarouselImageService carouselImageService;

	/**
	 * 上传文件方法
	 * 
	 * @param file
	 *            前台上传的文件对象
	 * @return
	 */
	@RequestMapping("/uploadCarousel")
	@ResponseBody
	public ResponseBean<Integer> uploadCarousel(@RequestParam("carousel") MultipartFile multfile) {
		ResponseBean<Integer> responseBean=new ResponseBean<Integer>();
		// 获取文件名
		String fileName = multfile.getOriginalFilename();
		// 获取文件后缀
		String suffix = fileName.substring(fileName.lastIndexOf("."));
        //上传文件名的前缀
		String uploadFileNamePrefix= Utils.createFileName();

		
		try {
			 
			 File file = File.createTempFile(uploadFileNamePrefix, suffix);
			// MultipartFile to File
			multfile.transferTo(file);
			
			// 上传至阿里云
			OSSClient ossClient = new OSSClient(OSSClientConstants.ENDPOINT, OSSClientConstants.ACCESS_KEY_ID,
					OSSClientConstants.ACCESS_KEY_SECRET);
            String key="slide/"+uploadFileNamePrefix+suffix;
			ossClient.putObject(OSSClientConstants.BUCKET_NAME, key, file);
			// 关闭client
			ossClient.shutdown();
			
			// 保存数据库
			Carousel carousel = new Carousel();
			carousel.setCarouseladdress("https://upload.95cfun.com/" + key);
			carousel.setType("0");		
			// 程序结束时，删除临时文件
		     deleteFile(file);
		     
		     responseBean= this.carouselImageService.insert(carousel);
		     
		     
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseBean.setErrorCode(ErrorCode.FILE_UPLOAD_EXCEPTION);
			responseBean.setErrorMessage("上传文件错误");
		}
		
		return responseBean;

		
		
	}
	
	
	/**  
     * 删除  
     *   
     * @param files  
     */  
    private void deleteFile(File... files) {  
        for (File file : files) {  
            if (file.exists()) {  
                file.delete();  
            }  
        }  
    }


	/**
	 * 查询当前所有轮转图
	 */
	@RequestMapping(value = "/queryCarousels", method = RequestMethod.GET)
	@ResponseBody
	public List<Carousel> queryCarouselList() {
		List<Carousel> carouselList = this.carouselImageService.queryCarousels();
		return carouselList;
	}

	/**
	 * 查询启用状态轮播图
	 */
	@RequestMapping(value = "/queryStartCarousels")
	@ResponseBody
	public List<Carousel> queryStartCarousels() {
		List<Carousel> startCarousels = this.carouselImageService.queryStartCarousels();
		return startCarousels;
	}

	/**
	 * 删除轮转图
	 */
	@RequestMapping(value = "/deleteCarouselByKey")
	@ResponseBody
	public ResponseBean<Integer> deleteCarouselByKey(int id) {
		return this.carouselImageService.deleteCarouselByKey(id);
	}

	/**
	 * 修改轮转图状态禁用
	 */
	@RequestMapping(value = "/forbiddenCarouselByKey")
	@ResponseBody
	public ResponseBean<Integer> forbiddenCarouselByKey(int id) {
		return this.carouselImageService.forbiddenCarouselByKey(id);
	}

	/**
	 * 启用
	 */
	@RequestMapping(value = "/startCarouselByKey")
	@ResponseBody
	public ResponseBean<Integer> startCarouselByKey(int id) {
		return this.carouselImageService.startCarouselByKey(id);
	}

	/**
	 * 添加备注
	 */
	@RequestMapping(value = "/addRemarkById")
	@ResponseBody
	public ResponseBean<Integer> addRemarkById(@RequestParam("id")Integer id, @RequestParam("remark")String remark) {
		return this.carouselImageService.addRemarkById(id, remark);
	}
}
