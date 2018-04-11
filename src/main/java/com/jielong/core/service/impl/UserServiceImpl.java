package com.jielong.core.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jielong.base.util.Constant;
import com.jielong.base.util.ErrorCode;
import com.jielong.base.util.NetworkConnection;
import com.jielong.base.util.Utils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.UserMapper;
import com.jielong.core.domain.User;
import com.jielong.core.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	
   @Autowired
   UserMapper userMapper;
    
   /**
    * 获取session_key 
    */
   public ResponseBean<Map<String,Object>> login(String code) {
	   
	    ResponseBean<Map<String, Object>> responseBean=new ResponseBean<Map<String,Object>>();
   	    Map<String, Object> resultMap=new HashMap<String, Object>();
	   
	    StringBuilder sb=new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?");
		sb.append("appid="+Constant.APPID+"&");
		sb.append("secret="+Constant.SECRET+"&");
		sb.append("js_code="+code+"&");
		sb.append("grant_type=authorization_code");
		
		String result=NetworkConnection.get(sb.toString());
		try {
		
			JSONObject resultJson=new JSONObject(result);
			String sessionKey=resultJson.getString("session_key"); //会话密钥
			
			String openId=resultJson.getString("openid");  //用户在微信平台的唯一标识
			
			//根据openId查询数据库是否有该用户
			List<User> users=userMapper.selectByOpenId(openId);
			if (users.size()>0) {
				resultMap.put("sessionId", users.get(0).getSessionId());
				resultMap.put("userId", users.get(0).getId());
			}
			else {
			
			// 生成一个唯一字符串sessionid作为键，将openid和session_key作为值，存入数据库，超时时间设置为2小时
			String sessionId=System.currentTimeMillis()+Utils.createRandomNum(6);
			
			User user=new User();
			user.setSessionId(sessionId);
			user.setSessionValue(sessionKey+openId);
			user.setSessionKey(sessionKey);
			
			user.setOpenId(openId);
			
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			//user.setValidtime(7200000); 设置有效时间，单位毫秒，默认 720000
			
			userMapper.insertSelective(user);
		     
			resultMap.put("sessionId", sessionId);
			Integer userID=userMapper.selectByOpenId(openId).get(0).getId();
			
			resultMap.put("userId", userID);
			}
			responseBean.setData(resultMap);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseBean.setErrorCode(ErrorCode.COMMON_EXCEPTION);
			responseBean.setErrorMessage("获取session_key发生错误");
			
		}
		
			return responseBean;
		
   }	
   
  

}
