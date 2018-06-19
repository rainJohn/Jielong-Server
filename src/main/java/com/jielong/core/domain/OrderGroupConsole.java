package com.jielong.core.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderGroupConsole {
    private Integer id;

    private Integer jielongId;

    private Integer goodsId;

    private Integer groupOkFlg;

    private Integer consoleFlg;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT-7")
    private Date createdAt;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT-7")
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJielongId() {
        return jielongId;
    }

    public void setJielongId(Integer jielongId) {
        this.jielongId = jielongId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGroupOkFlg() {
        return groupOkFlg;
    }

    public void setGroupOkFlg(Integer groupOkFlg) {
        this.groupOkFlg = groupOkFlg;
    }

    public Integer getConsoleFlg() {
        return consoleFlg;
    }

    public void setConsoleFlg(Integer consoleFlg) {
        this.consoleFlg = consoleFlg;
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