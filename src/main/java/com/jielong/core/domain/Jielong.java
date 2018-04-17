package com.jielong.core.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Jielong {
    private Integer id;
    
    private Integer userId;

    private String topic;

    private String description;

    private String addressName;

    private String addressDetail;
    
    private Double addressLongitude;  //经度
    
    private Double addressLatitude;   //纬度
    
    protected String createTimeStr;
	protected String updateTimeStr;
   

    private Integer setFinishTime;
    
  
    private String finishTime;

    private String introImages;

    private String goodsAddresses;
    
    private String phoneNumber;
    
    private Integer browseSum;
    
    private Integer joinSum;
    
    private BigDecimal joinMoney;
    
   

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    
    private Integer status;     //接龙状态 1：进行中 2：活动结束  3：提前终止 4：其他情况

    //商品列表
    private List<Goods> goodsList;
    
    //图片介绍数组
    private List<String> imageList;
    
    //提货地址数组
    private List<UserAddress>  takeGoodsAddressList;
    
    private UserInfo userInfo;
    

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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Integer getSetFinishTime() {
		return setFinishTime;
	}

	public void setSetFinishTime(Integer setFinishTime) {
		this.setFinishTime = setFinishTime;
	}

	
	public Double getAddressLongitude() {
		return addressLongitude;
	}

	public void setAddressLongitude(Double addressLongitude) {
		this.addressLongitude = addressLongitude;
	}

	public Double getAddressLatitude() {
		return addressLatitude;
	}

	public void setAddressLatitude(Double addressLatitude) {
		this.addressLatitude = addressLatitude;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getIntroImages() {
		return introImages;
	}

	public void setIntroImages(String introImages) {
		this.introImages = introImages;
	}

	public String getGoodsAddresses() {
		return goodsAddresses;
	}

	public void setGoodsAddresses(String goodsAddresses) {
		this.goodsAddresses = goodsAddresses;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public List<UserAddress> getTakeGoodsAddressList() {
		return takeGoodsAddressList;
	}

	public void setTakeGoodsAddressList(List<UserAddress> takeGoodsAddressList) {
		this.takeGoodsAddressList = takeGoodsAddressList;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBrowseSum() {
		return browseSum;
	}

	public Integer getJoinSum() {
		return joinSum;
	}



	public void setBrowseSum(Integer browseSum) {
		this.browseSum = browseSum;
	}

	public void setJoinSum(Integer joinSum) {
		this.joinSum = joinSum;
	}

	public BigDecimal getJoinMoney() {
		return joinMoney;
	}

	public void setJoinMoney(BigDecimal joinMoney) {
		this.joinMoney = joinMoney;
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