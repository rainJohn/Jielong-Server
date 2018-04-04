package com.jielong.core.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GoodsClass {
	
    protected Integer id;
    protected String createTimeStr;
	protected String updateTimeStr;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createdAt;	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date updatedAt;
	private String className;
	private List<GoodsSubClass> goodsSubClasses;
	
	private Integer flag;
	
	
	public Integer getId() {
		return id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public GoodsClass(String className) {
		
		this.className = className;
	}
    public GoodsClass() {		
		
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<GoodsSubClass> getGoodsSubClasses() {
		return goodsSubClasses;
	}
	public void setGoodsSubClasses(List<GoodsSubClass> goodsSubClasses) {
		this.goodsSubClasses = goodsSubClasses;
	}
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	

	public String getCreateTimeStr() {
		TimeZone timeZone = TimeZone.getDefault();  
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		simpleDateFormat.setTimeZone(timeZone);  
		if(getCreatedAt()!=null) {
		  return simpleDateFormat.format(getCreatedAt());
		}else {
			return null;
		}   
	
	}

	public String getUpdateTimeStr() {
		TimeZone timeZone = TimeZone.getDefault();  
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		simpleDateFormat.setTimeZone(timeZone);  
		if (getUpdatedAt()!=null) {
			return simpleDateFormat.format(getUpdatedAt());   
		}else {
			return null;
		}
		
	
	}

	
}
