package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.beans.SignBean;
import com.jielong.core.beans.SignPickBean;
import com.jielong.core.domain.Order;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

  
    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id); 

    int updateByPrimaryKey(Order record);
    
    //参与的接龙
    @Select("select * from jielong_order where user_id=#{userId}")
    List<Order> selectByUserId(@Param("userId") Integer userId);
    
    //发起的接龙
    @Select("select * from jielong_order where jielong_id in (select id from jielong where user_id=#{userId}")
    List<Order> selectByPublisherId(@Param("userId") Integer userId);
    
    //根据接龙id查询订单
    @Select("select * from jielong_order where jielong_id=#{jielongId} order by created_at asc")
    List<Order> selectByJielongId(@Param("jielongId")Integer jielongId);
    
    //设置自提标记
   // @Update("update jielong_order set state=3 where order_num in #{orderNumList}")
    Integer signPick(SignBean signBean);
    
}