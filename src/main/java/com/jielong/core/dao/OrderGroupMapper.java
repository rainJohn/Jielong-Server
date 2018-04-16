package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    

    //关闭接龙，最终结果更新
    @Update("update order_group set trade_flg = #{tradeFlg},order_flg = #{orderFlg} where jielong_id = #{jielongId} and goods_id = #{goodsId} and trade_flg = 0 and order_flg = 0")
    int updateLastStateFlg(@Param("tradeFlg") Integer tradeFlg,@Param("orderFlg") Integer orderFlg,@Param("jielongId") Integer jielongId,@Param("goodsId") Integer goodsId);

    //设置自提标记
   // @Update("update order_group set trade_flg=3 where order_id in #{orderNumList}")
    int signPick( List<String> orderNumList);
    
    //根据接龙id查询订单
    @Select("select * from order_group where jielong_id=#{jielongId}")
    List<OrderGroup> selectByJielongId(@Param("jielongId")Integer jielongId);
    
    //根据接龙id查询已经拼团成功，待提货的订单
    @Select("select * from order_group where jielong_id=#{jielongId} and trade_flg=2")
    List<OrderGroup> selectPickByJielongId(@Param("jielongId")Integer jielongId);

    //根据接龙id查询goodsID
    @Select("select distinct goods_id from order_group where jielong_id=#{jielongId}")
    List<Integer> selectGetGoodsId(@Param("jielongId")Integer jielongId);
    
    //根据商品id查询订单
    @Select("select * from order_group where goods_id=#{goodsId} order by created_at desc")
    List<OrderGroup> selectByGoodsId(@Param("goodsId")Integer goodsId);

    //根据接龙id查询交易已经完成的订单
    @Select("select * from order_group where jielong_id=#{jielongId} and (trade_flg=3 or trade_flg=0 or trade_flg=2) order by created_at asc")
    List<OrderGroup> selectFinishByJielongId(@Param("jielongId")Integer jielongId);

    
}