package com.jielong.core.domain;

import java.util.List;

public class GoodsClass extends ParentDomain{
	
	
	private String className;
	private List<GoodsSubClass> goodsSubClasses;
	
	private Integer flag;
	
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
	
}
