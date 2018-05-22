package com.jielong.core.dao;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jielong.core.beans.JlConditionsBean;
import com.jielong.core.domain.Jielong;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JielongDaoTest {
  @Autowired
  JielongMapper jielongMapper;
	
 /**
  * 按条件查询	
  */
  @Test
  public void selectByConditionsTest() {
	  
	  JlConditionsBean bean=new JlConditionsBean();
	 // bean.setTopic("牛排");
	  bean.setUserNickName("迪欧");
	  
	  List<Jielong> list=jielongMapper.selectByConditions(bean);
	  list.forEach(jielong->System.out.println(jielong.getTopic()));
  }
  
  
  /**
   * 删除接龙
   */
  @Test
  public void deleteTest() {
    int result=jielongMapper.deleteJielong(142);
    
    Assert.assertEquals(1, result);
	  
  }
	
}
