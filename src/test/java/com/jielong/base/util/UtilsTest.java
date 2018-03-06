package com.jielong.base.util;

import java.util.Random;

import org.junit.Test;

public class UtilsTest {
	
	
	@Test
	public void createRandom() {
		Random random=new Random();
		System.out.println(System.currentTimeMillis());
		for(int i=0;i<5;i++) {
			System.out.println(random.nextLong());
		}
	}
	
	

}
