package com.jielong.core.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserMessage {
	private Integer id;

	private Integer userId;

	private String title;

	private String message;

	private Integer jielongId;

	private Integer isRead = 0; // 是否已读，默认为0，未读

	protected String createTimeStr;
	protected String updateTimeStr;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC-7")
	private Date createTime;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="UTC-7")
	private Date updateTime;

	private List<Integer> userIdList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message == null ? null : message.trim();
	}

	public Integer getJielongId() {
		return jielongId;
	}

	public void setJielongId(Integer jielongId) {
		this.jielongId = jielongId;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}

	public String getCreateTimeStr() {
		TimeZone timeZone = TimeZone.getDefault();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setTimeZone(timeZone);
		if (getCreateTime() != null) {
			return simpleDateFormat.format(getCreateTime());
		} else {
			return null;
		}

	}

	public String getUpdateTimeStr() {
		TimeZone timeZone = TimeZone.getDefault();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simpleDateFormat.setTimeZone(timeZone);
		if (getUpdateTime() != null) {
			return simpleDateFormat.format(getUpdateTime());
		} else {
			return null;
		}

	}

}