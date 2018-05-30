package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.JielongMapper;
import com.jielong.core.dao.UserAddressMapper;
import com.jielong.core.domain.UserAddress;
import com.jielong.core.service.UserAddressService;

@Service
public class UserAddressServiceImpl implements UserAddressService {
	
	@Autowired
	UserAddressMapper userAddressMapper;
	@Autowired
	JielongMapper jielongMapper;

	@Override
	public ResponseBean<Integer> insertSelective(UserAddress address) {
		int result=userAddressMapper.insertSelective(address);
		ResponseBean<Integer> responseBean=new ResponseBean<Integer>(result);
		return responseBean;
	}

	@Override
	public ResponseBean<Integer> deleteById(Integer id) {
		
		ResponseBean<Integer> responseBean=new ResponseBean<Integer>();
		
		//查询有没有接龙使用该地址
		List<String> addressIdList=jielongMapper.selectAddress(id.toString());
		if (addressIdList!=null && addressIdList.size()>0) {
			for (String string : addressIdList) {
				String[] addressArray=string.split(",");
				if (addressArray.length>0) {
					for (String address : addressArray) {
						if (address.equals(id.toString())) {
							responseBean.setErrorCode(ErrorCode.DELETE_EXCEPTION);
							responseBean.setErrorMessage("该地址正在使用中，暂时无法删除！");
							return responseBean;
						}
					}
				}
				
			}
		}
		
		
		int result=userAddressMapper.deleteByPrimaryKey(id);
		responseBean.setData(result);
		return responseBean;
	}

	@Override
	public ResponseBean<Integer> updateById(UserAddress address) {
		address.setUpdatedAt(new Date());
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
