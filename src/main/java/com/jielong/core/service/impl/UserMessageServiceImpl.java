package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.UserMessageMapper;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.UserMessageService;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    UserMessageMapper userMessageMapper;
	
	@Override
	public ResponseBean<Integer> insert(UserMessage userMessage) {
		Integer result=userMessageMapper.insertSelective(userMessage);
		return new ResponseBean<>(result);
	}

	@Override
	public ResponseBean<Integer> update(UserMessage userMessage) {
		userMessage.setUpdateTime(new Date());
		Integer result=userMessageMapper.updateByPrimaryKeySelective(userMessage);
		return new ResponseBean<>(result);
	}

	@Override
	public ResponseBean<List<UserMessage>> selectAll() {
		List<UserMessage> userMessages=userMessageMapper.selectAll(); 
		return new ResponseBean<List<UserMessage>>(userMessages);
	}

	@Override
	public ResponseBean<Integer> delete(Integer id) {
	    Integer result=userMessageMapper.deleteByPrimaryKey(id);
		return new ResponseBean<>(result);
	}
	@Override
	public ResponseBean<Integer> updateReadeState(Integer id) {
        Integer result=userMessageMapper.updateReadState(id);
		return new ResponseBean<Integer>(result);
	}
	
	@Override
	public ResponseBean<Integer> insertBatch(UserMessage userMessage) {

		return new ResponseBean<Integer>(userMessageMapper.insertBatch(userMessage));
	}
	
	@Override
	public ResponseBean<List<UserMessage>> selectByUserId(Integer userId) {
		return new ResponseBean<List<UserMessage>>(userMessageMapper.selectByUserId(userId));
	}
	
	

}
