package com.jielong.base.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
	
	
	@Test
	public void testCollectionSort() {
		
	  Integer[]  nums= {3,2,1,5,4};	
	  
      List<Integer> numList=Arrays.asList(nums);
      
      System.out.println("排序前：");
      
      numList.stream().forEach(num->System.out.println(num));
      
      System.out.println("正向排序后：");
      
      numList.stream().sorted((a,b)->a-b).forEach(num->System.out.println(num));
      numList.stream().sorted(new Comparator<Integer>() {

		@Override
		public int compare(Integer o1, Integer o2) {
			// TODO Auto-generated method stub
			return o1-o2;
		   }
	    }).filter(num->num>3).collect(Collectors.toList());
      
      System.out.println("逆向排序后：");
      
      numList.stream().sorted((a,b)->b-a).forEach(num->System.out.println(num));      
		
	}
	

}
