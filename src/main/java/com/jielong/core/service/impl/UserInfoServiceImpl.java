package com.jielong.core.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.aliyun.oss.OSSClient;
import com.jielong.base.util.OSSClientConstants;
import com.jielong.base.util.Utils;
import com.jielong.core.service.QRcodeService;
import org.apache.commons.lang.StringUtils;
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
	@Autowired
	QRcodeService qRcodeService;
	
	
	
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
	public ResponseBean<Integer> update(UserInfo userInfo) {
		userInfo.setUpdatedAt(new Date());
		Integer result=userInfoMapper.updateByPrimaryKeySelective(userInfo);
		return new ResponseBean<Integer>(result);
	}

    @Override
    public ResponseBean<List<UserInfo>> selectAll() {
    	List<UserInfo> list=userInfoMapper.selectAll();
    	return new ResponseBean<>(list);
    }
    
    /**
     * 根据条件查询
     */
    @Override
    public ResponseBean<List<UserInfo>> selectByConditions(UserInfo conditionUserInfo) {
    	
    	List<UserInfo> list=userInfoMapper.selectByConditions(conditionUserInfo);
    	return new ResponseBean<List<UserInfo>>(list);
    }

	/**
	 * 查询二维码url
	 * @param userId
	 * @return
	 */
	@Override
	public ResponseBean<String> selectQRcodeUrl(Integer userId) {
		ResponseBean<String> responseBean=new ResponseBean<>();
		//1、首先查询该用户的二维码url是否为空
		String url=userInfoMapper.selectByUserId(userId).get(0).getQrcodeUrl();
		//2、如果为不为null
		if (StringUtils.isNotEmpty(url)){
			responseBean.setData(url);
		}else {  //TODO:3、如果为null，生成二维码，并保存至oss,更新用户信息
			 String path="pages/index/index?parentUserId="+userId;
			 //获取到二维码图片文件
             File imageFile=qRcodeService.getQRcodeImage(path);
            //上传OSS
            String imageUrl=ossUpload(imageFile);
            responseBean.setData(imageUrl);

            //更新用户信息
			userInfoMapper.updateQrcode(imageUrl,userId);
		}

		return responseBean;
	}

	public String ossUpload(File file) {
		String domain="https://jielongtest.oss-cn-shanghai.aliyuncs.com/";

		OSSClient ossClient = new OSSClient(OSSClientConstants.ENDPOINT, OSSClientConstants.ACCESS_KEY_ID, OSSClientConstants.ACCESS_KEY_SECRET);
		// 上传文件
		String fileName="qrcode/"+Utils.createFileName()+".png";
		ossClient.putObject(OSSClientConstants.BUCKET_NAME, fileName, file);
		// 关闭client
		ossClient.shutdown();
		return  domain+fileName;
	}
}
