package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.GoodsSubClass;
import com.jielong.core.service.GoodsSubClassService;

@RestController
public class GoodsSubClassController {

	@Autowired
	GoodsSubClassService goodsSubClassService;
	
	/**
	 * 插入商品分类子类
	 * */
	@RequestMapping("/addGoodsSubClass")
	public ResponseBean<Integer> addGoodsSubClass(@RequestBody GoodsSubClass goodsSubClass){
		goodsSubClass.setFlag("0");
		return goodsSubClassService.addGoodsSubClass(goodsSubClass);
	}
	
	/**
	 * 修改
	 * */
	@RequestMapping("updateGoodsSubClass")
	public ResponseBean<Integer> updateGoodsSubClass(@RequestBody GoodsSubClass goodsSubClass){
		return goodsSubClassService.updateGoodsSubClass(goodsSubClass);
	}
	
	/**
	 * 删除
	 * */
	@RequestMapping("deleteGoodsSubClassById")
	public ResponseBean<Integer> deleteGoodsSubClassById(@RequestParam("id") Integer id){
		return goodsSubClassService.deleteGoodsSubClassById(id);
	}
	
}
