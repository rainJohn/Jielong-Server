package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.UserInfoMapper;
import com.jielong.core.domain.UserInfo;
import com.jielong.core.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	UserInfoMapper userInfoMapper;
	
	@Override
	public ResponseBean<Integer> insert(UserInfo userIfo) {
		ResponseBean<Integer>  responseBean=new ResponseBean<Integer>();
		List<UserInfo> userInfos=userInfoMapper.selectByUserId(userIfo.getUserId());
		if(userInfos.size()<=0) {
			 Integer result=userInfoMapper.insertSelective(userIfo); 	
			 responseBean.setData(result);
		}else {
			responseBean.setData(0);
		}
		return responseBean;
	   
	}

	@Override
	public ResponseBean<UserInfo> selectByUserId(Integer userId) {
		ResponseBean<UserInfo> responseBean=new ResponseBean<UserInfo>();
		List<UserInfo> userInfos=userInfoMapper.selectByUserId(userId);
		if (userInfos!=null&&userInfos.size()>0) {
			responseBean.setData(userInfos.get(0));
			return responseBean;
		}else {
	        responseBean.setErrorCode(ErrorCode.NO_DATA_EXCEPTION);
	        responseBean.setErrorMessage("没有查询到该用户的数据");
	        return responseBean;
		}
	}

	@Override
	public ResponseBean<Integer> update(UserInfo userIfo) {
		userIfo.setUpdatedAt(new Date());
		Integer result=userInfoMapper.updateByPrimaryKeySelective(userIfo);
		return new ResponseBean<Integer>(result);
	}

}
