package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.PageBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Jielong;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGroupService;

@RestController
@RequestMapping("/jielong")
public class JielongController {

	@Autowired
	JielongService jielongService;
	@Autowired
	OrderGroupService orderGroupService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody Jielong jielong){
		return  jielongService.insert(jielong);
	}
	
	/**
	 * 分页查询
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("/selectByPage")
	public ResponseBean<List<Jielong>> selectByPage(@RequestBody PageBean pageBean){
		return jielongService.selectByPage(pageBean);
	}
	/**
	 * 根据userId查询
	 *
	 */
	@RequestMapping("/selectByUserId")
	public ResponseBean<List<Jielong>> selectByUserId(@RequestParam("userId") Integer userId){
		return jielongService.selectByUserId(userId);
	}
    /**
     * 更新接龙
     * 
     */
	@RequestMapping("/update")
	public ResponseBean<Integer> update(@RequestBody Jielong jielong){
		return jielongService.update(jielong);
	}
	/**
	 * 结束接龙
	 */
	@RequestMapping("/closeJielong")
	public ResponseBean<Integer> closeJielong(@RequestParam("id") Integer id){
		 orderGroupService.closeJieLong(id);
         return jielongService.closeJielong(id);                   		
	}
	
	/**
	 * 更新浏览人数
	 * @param jieLongId
	 * @return
	 */
	@RequestMapping("/updateBrowse")
	public ResponseBean<Integer> updateBrowse(@RequestParam("id") Integer jieLongId){
		return jielongService.updateBrowse(jieLongId);
	}
	
	/**
	 * 查询所有记录数
	 */
	@RequestMapping("/selectCount")
	public ResponseBean<Integer> selectCount(){
		return jielongService.selectCount();
	}
	
	/**
	 * 根据接龙id查询
	 */
	@RequestMapping("/selectById")
	public ResponseBean<Jielong> selectById(@RequestParam("id") Integer jieLongId){
		
		return jielongService.selectById(jieLongId);
		
	}
    	
}
