package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jielong.core.domain.OrderGoods;

@Mapper
public interface OrderGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGoods record);

    int insertSelective(OrderGoods record);

    OrderGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGoods record);

    int updateByPrimaryKey(OrderGoods record);
    
    @Select("select * from order_goods where order_id=#{orderId}")
    List<OrderGoods> selectByOrderId(@Param("orderId")Integer orderId);
    //根据商品id查询记录
    @Select("select * from order_goods where goods_id=#{goodsId} order by created_at desc")
    List<OrderGoods> selectByGoodsId(@Param("goodsId") Integer goodsId);
}