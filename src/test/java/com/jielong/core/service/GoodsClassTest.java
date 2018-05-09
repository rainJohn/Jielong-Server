package com.jielong.core.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jielong.core.domain.GoodsClass;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsClassTest {
  
	@Autowired
	GoodsClassService goodsClassService;
	
	
	@Test
	public void testSelect() {
		List<GoodsClass> goodsClassList= goodsClassService.selectAllClass();
		
		System.out.println("---");
		System.out.println(goodsClassList.size());
		System.out.println(goodsClassList.get(0).getClassName());
		
	}
	
}
