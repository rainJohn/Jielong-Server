package com.jielong.core.service;

import java.util.List;

import com.jielong.core.beans.PickCountBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Order;


public interface OrderGroupService {
  ResponseBean<Integer> insert(Order order);
  
  ResponseBean<List<Order>> selectByCustomerId(Integer userId);
	
  ResponseBean<List<Order>> selectByPublisherId(Integer userId);
  
  int getGroupPeople(Integer jielongId,Integer goodsId);
  

  int closeJieLong(Integer jielongId);

  ResponseBean<List<Order>> selectByJielongId(Integer jielongId);
  
  ResponseBean<Integer> signPick(List<String> orderNumList);
   

  
  ResponseBean<List<PickCountBean>>  countPick(Integer jielongId);


  ResponseBean<List<Order>> selectPickByJielongId(Integer jielongId);   
  ResponseBean<List<Order>> selectJoinByJielongId(Integer jielongId);

}
