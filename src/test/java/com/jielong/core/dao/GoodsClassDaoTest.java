package com.jielong.core.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jielong.core.domain.GoodsClass;
import com.jielong.core.domain.User;


/**
 * 测试Dao
 * @author cxy
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsClassDaoTest {
  @Autowired
  GoodsClassDao goodsClassDao;
  @Autowired
  UserMapper userMapper;
  
  /**
   * 测试查找一条记录
   */
  @Test
  public void testFindOne() {
	  
	  GoodsClass goodsClass=goodsClassDao.findById(42);
	  System.err.println(goodsClass.getClassName());
	  
	  
  }
  
 
  @Test
  public void testSelectUser() {
	  User user=userMapper.selectByPrimaryKey(43);
	  Assert.assertNotNull(user);
  }
	
}
