package com.jielong.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.jielong.core.beans.PageBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Jielong;
import com.mysql.fabric.Response;

public interface JielongService {

	
	
   ResponseBean<Integer> insert(Jielong jielong);
   
   ResponseBean<List<Jielong>> selectByPage(PageBean pageBean);
   
   ResponseBean<List<Jielong>> selectByUserId(Integer userId);
   
   ResponseBean<Integer> update(Jielong jielong);
   
   //更新浏览人数
   ResponseBean<Integer> updateBrowse(Integer jielongId);
   
   //更新参与人数和接龙金额
   ResponseBean<Integer> updateJoin(Integer id,BigDecimal joinMoney);
   
   

}
