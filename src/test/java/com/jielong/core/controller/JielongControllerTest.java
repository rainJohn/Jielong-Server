package com.jielong.core.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JielongControllerTest {

	@Autowired    
	private WebApplicationContext wac;  	
		
	private MockMvc mockMvc; 

	@Before    
	public void setup() {    
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();    
	}
	
	
	/**
	 * 条件查询
	 * @throws Exception
	 */
	@Test
	public void testSelectByConditions() throws Exception {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("topic", "接龙");
		
		
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/jielong/selectByConditions")
				        .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toJSONString()))
		                .andExpect(MockMvcResultMatchers.status().isOk())
		                .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		
	}
	
	
}
