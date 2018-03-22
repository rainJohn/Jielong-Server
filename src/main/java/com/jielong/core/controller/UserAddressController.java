package com.jielong.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.ResponseBean;

import com.jielong.core.domain.UserAddress;
import com.jielong.core.service.UserAddressService;

@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
 
	@Autowired
	UserAddressService userAddressService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody UserAddress userAddress){
	   return userAddressService.insertSelective(userAddress);	
		
	}
	@RequestMapping("/deleteById")
	public ResponseBean<Integer> delete(@RequestParam("id") Integer id){
		return userAddressService.deleteById(id);
	}
	@RequestMapping("/updateById")
	public ResponseBean<Integer> update(@RequestBody UserAddress userAddress){
		return userAddressService.updateById(userAddress);
	}
	
	@RequestMapping("/selectByUserId")
	public ResponseBean<List<UserAddress>> selectByUserId(@RequestParam("userId") Integer userId){
		return userAddressService.selectByUserId(userId);
	}

}
