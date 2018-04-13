package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.OrderGroup;

@Mapper
public interface OrderGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGroup record);

    int insertSelective(OrderGroup record);

    OrderGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGroup record);

    int updateByPrimaryKey(OrderGroup record);
    
    @Select("select sum(cust_buy_num) from order_group where jielong_id = #{jielongId} and goods_id = #{goodsId} and trade_flg = 0 and order_flg = 0 group by jielong_id,goods_id")
    Integer selectByCustBuyNum(@Param("jielongId") Integer jielongId,@Param("goodsId") Integer goodsId);
    
    @Select("select distinct cust_id from order_group where jielong_id = #{jielongId} and goods_id = #{goodsId} and trade_flg = 0 and order_flg = 0 ")
    List<Integer> selectByUserId(@Param("jielongId") Integer jielongId,@Param("goodsId") Integer goodsId);
    
    @Select("select * from order_group where order_id = #{orderId}")
    List<OrderGroup> selectByOrderId(@Param("orderId") String orderId);
    
    //参与的接龙
    @Select("select * from order_group where cust_id = #{custId}")
    List<OrderGroup> selectByCustId(@Param("custId") Integer custId);
    
    //发起的接龙
    @Select("select * from order_group where jielong_id in (select id from jielong where user_id=#{userId}")
    List<OrderGroup> selectByPublisherId(@Param("userId") Integer userId);
    
    
}