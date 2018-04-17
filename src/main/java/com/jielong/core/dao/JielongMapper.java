package com.jielong.core.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jielong.core.domain.Jielong;

@Mapper
public interface JielongMapper {
	
    int deleteByPrimaryKey(Integer id);
    
    int insertSelective(Jielong record);

    Jielong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jielong record);

  
    
    @Select("select * from jielong order by created_at desc")
    List<Jielong> selectAll();
    
    @Select("select * from jielong where user_id= #{userId}")
    List<Jielong> selectByUserId(@Param("userId") Integer userId);
    
    //更新浏览人数
    @Update("update jielong set browse_sum=browse_sum+1 where id= #{id}")
    int updateBrowse(@Param("id") Integer id);
    
    //更新参与人数和接龙金额
    @Update("update jielong set join_sum=join_sum+1,join_money=join_money+#{joinMoney} where id=#{id}")
    int updateJoin(@Param("id") Integer id,@Param("joinMoney") BigDecimal joinMoney);
    
    @Select("select count(*) from jielong")
    Integer  selectCount();
    
    @Select("select topic from jielong where id=#{id}")
    String selectTopic(@Param("id")Integer id);
    
    /**
     * 设置接龙的状态为结束 ：如果当前的时间大于Jielong的结束时间
     * @return
     */
    @Update("update jielong set status=2  where  finish_time is not null and DATE_FORMAT(NOW(),'%Y/%m/%d %H:%i') > str_to_date(finish_time,'%Y/%m/%d %H:%i')")
    Integer setFinishStatus();
}