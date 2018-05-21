package com.jielong.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.jielong.core.beans.JlConditionsBean;
import com.jielong.core.beans.PageBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Jielong;

public interface JielongService {

	
	
   ResponseBean<Integer> insert(Jielong jielong);
   
   /**
    * 分页查询所有可用的接龙
    * @param pageBean
    * @return
    */
   ResponseBean<List<Jielong>> selectByPage(PageBean pageBean);
   
   /**
    * 查询所有的接龙
    * @param pageBean
    * @return
    */
   ResponseBean<List<Jielong>> selectAll(PageBean pageBean);
   
   ResponseBean<List<Jielong>> selectByUserId(Integer userId);
   
   ResponseBean<Integer> update(Jielong jielong);
   
   //更新浏览人数
   ResponseBean<Integer> updateBrowse(Integer jielongId);
   
   //更新参与人数和接龙金额
   ResponseBean<Integer> updateJoin(Integer id,BigDecimal joinMoney);
   
   ResponseBean<Integer> selectCount(); 
   
   ResponseBean<Integer> selectAllCount();
   
   ResponseBean<Jielong> selectById(Integer id);
   //结束接龙
   ResponseBean<Integer> closeJielong(Integer id);
   
   //删除接龙
   ResponseBean<Integer> deleteJielong(Integer id);
   
   ResponseBean<List<Jielong>> selectByConditions(JlConditionsBean bean);
   
   

}
