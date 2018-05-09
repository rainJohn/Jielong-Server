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

/**
 * 测试controller
 * @author cxy
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsClassControllerTest {

// 注入WebApplicationContext  
@Autowired    
private WebApplicationContext wac;  	
	
// 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。	
private MockMvc mockMvc; 

@Before    
public void setup() {    
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();    
}    

//测试查询controller
@Test
public void testSelect() throws Exception{
	MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/getAllGoodsClass"))
			         .andExpect(MockMvcResultMatchers.status().isOk())
			         .andReturn();
	
	System.out.println(result.getResponse().getContentAsString());
	
	
}

@Test
//测试更新controller
public void testUpdate() throws Exception {
	
	JSONObject jsonObject=new JSONObject();
	jsonObject.put("id", "42");
	jsonObject.put("className", "景点");
	
	mockMvc.perform(MockMvcRequestBuilders.post("/updateGoodsClass")
			        .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toJSONString()))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(1));
	       
	
	
}


	

}
