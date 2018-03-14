package com.jielong.core.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Jielong {
    private Integer id;

    private String topic;

    private String description;

    private String addressName;

    private String addressDetail;

    private Integer setFinishTime;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    private String introImages;

    private String goodsAddresses;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    //商品列表
    private List<Goods> goodsList;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName == null ? null : addressName.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public Integer getSetFinishTime() {
        return setFinishTime;
    }

    public void setSetFinishTime(Integer setFinishTime) {
        this.setFinishTime = setFinishTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getIntroImages() {
        return introImages;
    }

    public void setIntroImages(String introImages) {
        this.introImages = introImages == null ? null : introImages.trim();
    }

    public String getGoodsAddresses() {
        return goodsAddresses;
    }

    public void setGoodsAddresses(String goodsAddresses) {
        this.goodsAddresses = goodsAddresses == null ? null : goodsAddresses.trim();
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

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
    
}