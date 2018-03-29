package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.PageBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Jielong;

public interface JielongService {

	
	
   ResponseBean<Integer> insert(Jielong jielong);
   
   ResponseBean<List<Jielong>> selectByPage(PageBean pageBean);
   
   ResponseBean<List<Jielong>> selectByUserId(Integer userId);
   
   ResponseBean<Integer> update(Jielong jielong);
   

}
