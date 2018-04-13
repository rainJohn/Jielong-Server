package com.jielong.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jielong.core.domain.Goods;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    
    //根据jielong_id 查询商品
    @Select("select * from goods where jielong_id = #{jielongId}")
    List<Goods> selectByJielongId(@Param("jielongId") Integer jielongId);
    //根据jielong_id 删除商品
    @Delete("delete from goods where  jielong_id=#{jielongId}")
    int deleteByJielongId(@Param("jielongId") Integer jielongId);
    
    //减少商品库存
    @Update("update goods set repertory=repertory-#{repertory} where id=#{id}")
    int updateRepertory(@Param("id") Integer id,@Param("repertory") Integer repertory);
    
    //根据jielong_id 查询商品id
    @Select("select id from goods where jielong_id = #{jielongId}")
    List<Integer> selectIdsByJielongId(@Param("jielongId") Integer jielongId);
}