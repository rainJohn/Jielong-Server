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

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

	@Autowired    
	private WebApplicationContext wac;  	
		
	private MockMvc mockMvc;
	@Before    
	public void setup() {    
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();    
	}
	
	@Test
	public void testSearch() {


	}
	
	
}
