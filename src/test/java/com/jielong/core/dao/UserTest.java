package com.jielong.core.dao;
import com.jielong.core.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
 @Autowired
 UserMapper userMapper;

 @Test
 public void testInsert(){
     User user=new User();
     user.setParentId(107);
     user.setSessionId("1000");
     int result=userMapper.insertSelective(user);
     Assert.assertEquals(1L,(long) result);

 }

 @Test
 public  void testUpdate(){
    User user=new User();
    user.setId(59);
    user.setParentId(1200);
    user.setSessionKey("10000");
    user.setSessionValue("1565878");
    int result=userMapper.updateByPrimaryKeySelective(user);
    Assert.assertEquals(1L,(long)result);

 }




}
