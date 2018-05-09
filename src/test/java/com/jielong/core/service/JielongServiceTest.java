package com.jielong.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.OrderGroupConsoleMapper;
import com.jielong.core.dao.UserMessageMapper;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGroupConsole;
import com.jielong.core.domain.UserMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JielongServiceTest {
	@Autowired
	JielongService jielongService;
//	@Autowired UserMessageService userMessageService;
	@Autowired
	UserMessageMapper userMessageMapper;
	@Autowired
	OrderGroupConsoleMapper orderGroupConsoleMapper;
	
	@Autowired
	OrderGroupService orderGroupService;
	
	@Test
	public void  testUpdateJoin() {	 
	  jielongService.updateJoin(12,new BigDecimal(200));
	}
	
	@Test
	public void testInsertBatch() {
		UserMessage userMessage=new UserMessage();
		userMessage.setTitle("测试消息");
		userMessage.setMessage("测试消息");
		userMessage.setIsRead(0);
		
		List<Integer> userIdList=new ArrayList<>();
		userIdList.add(0);
		userIdList.add(1);
		userIdList.add(2);
		userMessage.setUserIdList(userIdList);
		Integer result=userMessageMapper.insertBatch(userMessage);
		junit.framework.Assert.assertEquals(3, result.intValue());
	     
	}
	
	@Test
	public void testGroupConsole() {
		OrderGroupConsole orderGroupConsole = new OrderGroupConsole();
		orderGroupConsole.setJielongId(3);
		orderGroupConsole.setGoodsId(5);		
		orderGroupConsole.setGroupOkFlg(2);		
		orderGroupConsole.setConsoleFlg(0);
		orderGroupConsoleMapper.insertSelective(orderGroupConsole);
	}
	
	@Test
	public void testOrderGroup() {
		ResponseBean<List<Order>> responseBean=orderGroupService.selectByCustomerId(23);
		System.out.println(responseBean.getData());
		
		
	}
	

}
