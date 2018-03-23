package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.HelpMessageMapper;
import com.jielong.core.domain.HelpMessage;
import com.jielong.core.service.HelpMessageService;

@Service
public class HelpMessageServiceImpl implements HelpMessageService {
	
	@Autowired
	HelpMessageMapper helpMessageMapper;

	@Override
	public ResponseBean<Integer> insert(HelpMessage helpMessage) {
		
		return new ResponseBean<>(helpMessageMapper.insertSelective(helpMessage));
	}

	@Override
	public ResponseBean<Integer> deleteById(Integer id) {
		
		return new ResponseBean<Integer>(helpMessageMapper.deleteByPrimaryKey(id));
	}

	@Override
	public ResponseBean<Integer> update(HelpMessage helpMessage) {
		helpMessage.setUpdateTime(new Date());
		return new ResponseBean<Integer>(helpMessageMapper.updateByPrimaryKeySelective(helpMessage));
	}

	//查询所有数据
	@Override
	public ResponseBean<List<HelpMessage>> selectAll() {
		
		return new ResponseBean<List<HelpMessage>>(helpMessageMapper.selectAll());
	}

}
