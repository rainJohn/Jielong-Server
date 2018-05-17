package com.jielong.core.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.UserMessage;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMessageServiceTest {
	
@Autowired
UserMessageService userMessageService;
	
   
@Test
public void testInsertBatch() {
	
  UserMessage userMessage=new UserMessage();
  userMessage.setTitle("测试");
  userMessage.setMessage("测试详情");
  List<Integer> list=new ArrayList<Integer>();
  list.add(17);
  list.add(19);
  userMessage.setUserIdList(list);
  ResponseBean<Integer> responseBean=userMessageService.insertBatch(userMessage);
  
 // Assert.assertEquals(2, responseBean.getData());
	
	
}
	
}
