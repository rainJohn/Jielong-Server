package com.jielong.core.domain;

public class GoodsSubClass extends ParentDomain{

	private Integer parentClassId;
	private String className;


	public Integer getParentClassId() {
		return parentClassId;
	}

	public void setParentClassId(Integer parentClassId) {
		this.parentClassId = parentClassId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
