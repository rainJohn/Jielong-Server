package com.jielong.core.service;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.jielong.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class JielongServiceTest {
	@Autowired
	JielongService jielongService;
	
	@Test
	public void  testUpdateJoin() {	 
	  jielongService.updateJoin(12,new BigDecimal(200));
	}
	

}
