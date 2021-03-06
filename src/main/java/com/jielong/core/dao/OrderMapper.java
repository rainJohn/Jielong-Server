package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.beans.SignBean;
import com.jielong.core.domain.Order;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

  
    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id); 

    int updateByPrimaryKeySelective(Order record);
    
    //参与的接龙
    @Select("select * from jielong_order where user_id=#{userId}")
    List<Order> selectByUserId(@Param("userId") Integer userId);
    
    //发起的接龙
    @Select("select * from jielong_order where jielong_id in (select id from jielong where user_id=#{userId}")
    List<Order> selectByPublisherId(@Param("userId") Integer userId);
    
    //根据接龙id查询订单
    @Select("select * from jielong_order where jielong_id=#{jielongId} order by created_at asc")
    List<Order> selectByJielongId(@Param("jielongId")Integer jielongId);
    
    //根据订单编号查询订单
    @Select("select * from jielong_order where order_num=#{orderNum}")
    Order selectByOrderNum(@Param("orderNum")String orderNum);
    
    //设置自提标记
    Integer signPick(SignBean signBean);
    
     
    
}