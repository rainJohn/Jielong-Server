package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.UserMessage;

public interface UserMessageService {
	
	ResponseBean<Integer> insert(UserMessage userMessage);
	ResponseBean<Integer> update(UserMessage userMessage);
	ResponseBean<List<UserMessage>> selectAll();
	ResponseBean<Integer> delete(Integer id);
	ResponseBean<Integer> updateReadeState(Integer id);
	ResponseBean<Integer> insertBatch(UserMessage userMessage);

}
