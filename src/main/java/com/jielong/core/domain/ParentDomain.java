package com.jielong.core.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ParentDomain {
	protected Integer id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createdAt;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date updatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	

}
