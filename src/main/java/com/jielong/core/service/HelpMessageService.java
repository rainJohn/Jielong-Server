package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.HelpMessage;

public interface HelpMessageService {
	
	ResponseBean<Integer> insert(HelpMessage helpMessage);
	ResponseBean<Integer> deleteById(Integer id);
	ResponseBean<Integer> update(HelpMessage helpMessage);
	ResponseBean<List<HelpMessage>> selectAll();

}
