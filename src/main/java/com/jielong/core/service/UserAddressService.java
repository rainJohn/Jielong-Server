package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.User;
import com.jielong.core.domain.UserAddress;


/**
 * 用户自提地址service
 * @author cxy
 *
 */
public interface UserAddressService {
  	
	
  ResponseBean<Integer> insertSelective(UserAddress address);
  
  ResponseBean<Integer> deleteById(Integer id);
  
  ResponseBean<Integer> updateById(UserAddress address);
  
  ResponseBean<UserAddress> selectById(Integer id);
  
  //根据用户id查询所有地址
  ResponseBean<List<UserAddress>> selectByUserId(Integer userId);

}
