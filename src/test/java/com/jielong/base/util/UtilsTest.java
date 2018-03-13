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
	
/*	public double[] test(double a,double b,double a1, double b1, double sum1,double sum2) {
		double x=0,y=0;
		double tempX=(sum1-b*y)/a;
		
		
	}*/
	
	

}
