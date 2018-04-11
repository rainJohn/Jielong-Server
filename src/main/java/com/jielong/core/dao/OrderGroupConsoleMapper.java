package com.jielong.core.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.jielong.core.domain.OrderGroupConsole;

@Mapper
public interface OrderGroupConsoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGroupConsole record);

    int insertSelective(OrderGroupConsole record);

    OrderGroupConsole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGroupConsole record);

    int updateByPrimaryKey(OrderGroupConsole record);
    
    //更新成团状态 1为成 0 为不成
    @Update("update order_group_console set group_ok_flg = #{groupOkFlg} where jielong_id = #{jielongId} and goods_id = #{goodsId}")
    int updateGroupOkFlg(@Param("groupOkFlg") Integer groupOkFlg,@Param("jielongId") Integer jielongId,@Param("goodsId") Integer goodsId);
}