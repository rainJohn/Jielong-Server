package com.jielong.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.UserMessageMapper;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.UserMessageService;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    UserMessageMapper userMessageMapper;
    @Autowired
    OrderGroupMapper orderGroupMapper;
	
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
	
	

	@Override
	public ResponseBean<Integer> groupStateModify(Integer jieLongId, Integer goodsId, Integer setFlg) {
		
		List<Integer> listUserid = new ArrayList<Integer>();
		listUserid = orderGroupMapper.selectByUserId(jieLongId, goodsId);
		
		UserMessage userMessage = new UserMessage();
		
		if (listUserid!=null&&listUserid.size()>0) {
			if(setFlg == 1){
				//成团发送
				userMessage.setTitle("下单成功通知！");
				userMessage.setMessage("恭喜您，下单成功，您购买的订单已成团，订单详情可前往我的->我参与的接龙查看。");
				userMessage.setUserIdList(listUserid);
				this.insertBatch(userMessage);
				
			} else {
				//不成团发送
				userMessage.setTitle("群发参团状态改变通知！");
				userMessage.setMessage("由于有人取消参团，导致拼团人数暂不足，请等候拼团成功！订单详情可前往我的->我参与的接龙查看。");
				userMessage.setUserIdList(listUserid);
				this.insertBatch(userMessage);
			}
			
		}
		
		
		return null;
	}

}
