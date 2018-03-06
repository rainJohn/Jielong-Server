package com.jielong.core.dao;


import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.jielong.Application;
import com.jielong.core.dao.GoodsClassDao;
import com.jielong.core.domain.GoodsClass;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GoodsClassDaoTest {

   @Autowired
   GoodsClassDao goodsClassDao;
   
   @Test
   public void testInsert() {
	   GoodsClass goodsClass=new GoodsClass("测试分类");
	   goodsClassDao.insert(goodsClass);
	  
   }
   
   @Test
   public void testInsertById() {
	   String className=goodsClassDao.selectById(14).getClassName();
	   Assert.assertEquals("测试分类", className);
   }
   
   
}
