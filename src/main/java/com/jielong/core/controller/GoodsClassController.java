package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.GoodsClass;
import com.jielong.core.service.GoodsClassService;

@RestController
public class GoodsClassController {
	@Autowired GoodsClassService goodsClassService;
	
	@RequestMapping("/getAllGoodsClass")
	public ResponseBean<List<GoodsClass>> getAllGoodsClass(){
		ResponseBean<List<GoodsClass>> responseBean=new ResponseBean<List<GoodsClass>>(goodsClassService.selectAllClass());
		return  responseBean;
	}

}
