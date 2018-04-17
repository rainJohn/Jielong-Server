package com.jielong.base.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
	
	@Test
	public void testAddress() {
	   String s1="001.png,002.png,003.png";
	   String s2="001.png";
	   
	   String[] imgs=s1.split(",");
	   for(int i=0;i<imgs.length;i++) {
		   System.out.println(imgs[i]);
	   }
	
	   
		
	}
	
	@Test
	public void testDir() {
		System.out.println(System.getProperty("user.dir")+"\\uploadFiles\\") ;
	}
	@Test
	public void dateStr() {
		LocalDateTime dateTime=LocalDateTime.now();
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String dateTimeStr=dateTime.format(formatter);
		System.out.println(dateTimeStr);
	}
	
	@Test
	public void testAccessToken() {
		GetToken.getAccessToken();
	}
	
	@Test
	public void testTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String finishTime="2018/04/17 12:00";
		String now=simpleDateFormat.format(new Date());
		int result=now.compareTo(finishTime);
		System.out.println(result);
	}
	

}
