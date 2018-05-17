package com.jielong.core.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.UserMapper;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.UserMessageService;

@RestController
@RequestMapping("/userMessage")
public class UserMessageController {
	@Autowired
	UserMessageService userMessageService;
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody UserMessage userMessage){
		return userMessageService.insert(userMessage);
	}
	
	@RequestMapping("/update")
	public ResponseBean<Integer> update(@RequestBody UserMessage userMessage){
		return userMessageService.update(userMessage);
	}
	
	@RequestMapping("/selectAll")
	public ResponseBean<List<UserMessage>> selectAll(){
		return userMessageService.selectAll();
	}
	
	@RequestMapping("/deleteById")
	public ResponseBean<Integer> deleteById(@RequestParam("id") Integer id){
		return userMessageService.delete(id);
	}
	@RequestMapping("/updateReadStatus")
	public ResponseBean<Integer> updateReadStatus(@RequestParam("id") Integer id){
		return userMessageService.updateReadeState(id);
	}
	
	/**
	 * 给选中用户发送消息
	 * 批量插入 
	 * @param userMessage(必须包含    参数:userIdList)
	 * @return
	 */
	 @RequestMapping("/insertBatch")
	public ResponseBean<Integer> insertBatch(@RequestBody UserMessage userMessage){
		
		return userMessageService.insertBatch(userMessage);
		
	}
	
	 /**
	  * 给所有用户发送消息
	  * @return
	  */
	 public ResponseBean<Integer> insertAll(@RequestBody UserMessage userMessage){
	   ResponseBean<Integer> responseBean=new ResponseBean<Integer>();
       //先查询出所有的用户id
	   List<Integer> userIdList=userMapper.selectAllId();
	   if (userIdList!=null && userIdList.size()>0) {
		   userMessage.setUserIdList(userIdList);
		   responseBean=userMessageService.insertBatch(userMessage);
	    }else {
	    	responseBean.setErrorCode(ErrorCode.NO_DATA_EXCEPTION);
	    	responseBean.setErrorMessage("没有查询到用户信息");
	    }
	   return responseBean;
	 }
	
	//根据用户id查询消息
	@RequestMapping("/selectByUserId")
	public ResponseBean<List<UserMessage>> selectByUserId(@RequestParam("userId")Integer userId){
		return userMessageService.selectByUserId(userId);
	}
	

}
