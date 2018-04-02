package com.jielong.core.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserMessage {
    private Integer id;

    private Integer userId;

    private String title;

    private String message;

    private Integer jielongId;

    private Integer isRead=0;   //是否已读，默认为0，未读 
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

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
}