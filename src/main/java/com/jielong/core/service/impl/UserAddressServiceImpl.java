package com.jielong.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.UserAddressMapper;
import com.jielong.core.domain.UserAddress;
import com.jielong.core.service.UserAddressService;

@Service
public class UserAddressServiceImpl implements UserAddressService {
	
	@Autowired
	UserAddressMapper userAddressMapper;

	@Override
	public ResponseBean<Integer> insertSelective(UserAddress address) {
		int result=userAddressMapper.insertSelective(address);
		ResponseBean<Integer> responseBean=new ResponseBean<Integer>(result);
		return responseBean;
	}

	@Override
	public ResponseBean<Integer> deleteById(Integer id) {
		int result=userAddressMapper.deleteByPrimaryKey(id);
		ResponseBean<Integer> responseBean=new ResponseBean<Integer>(result);
		return responseBean;
	}

	@Override
	public ResponseBean<Integer> updateById(UserAddress address) {
		int result=userAddressMapper.updateByPrimaryKeySelective(address);
		ResponseBean<Integer> responseBean=new ResponseBean<Integer>(result);
		return responseBean;
	}

	@Override
	public ResponseBean<UserAddress> selectById(Integer id) {
		UserAddress userAddress=userAddressMapper.selectByPrimaryKey(id);
		ResponseBean<UserAddress> responseBean=new ResponseBean<UserAddress>(userAddress);
		return responseBean;
		
	}

	/**
	 * 根据userId查询
	 */
	@Override
	public ResponseBean<List<UserAddress>> selectByUserId(Integer userId) {
		ResponseBean<List<UserAddress>> responseBean=new ResponseBean<List<UserAddress>>();
		List<UserAddress> list=userAddressMapper.selectByUserId(userId);
		if (list!=null&&list.size()>0) {
			responseBean.setData(list);
			return responseBean;
		}else {
			responseBean.setErrorCode(ErrorCode.NO_DATA_EXCEPTION);
			responseBean.setErrorMessage("没有查询到相关数据");
			return responseBean;
		}
		
	}

}
