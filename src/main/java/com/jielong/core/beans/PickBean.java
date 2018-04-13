package com.jielong.core.beans;

import java.math.BigDecimal;

import com.jielong.core.domain.UserAddress;
import com.jielong.core.domain.UserInfo;


public class PickBean {
	
	
	private String userName;
	private String phoneNumber;	
	//自提点信息
	private UserAddress userAddress;
	//商品数量
	private Integer goodsSum;
	//商品单价
	private BigDecimal price;
	//备注
	private String remark;
	
	private UserInfo userInfo;
	

	
	public UserAddress getUserAddress() {
		return userAddress;
	}
	public Integer getGoodsSum() {
		return goodsSum;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public String getRemark() {
		return remark;
	}
	
	
	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}
	public void setGoodsSum(Integer goodsSum) {
		this.goodsSum = goodsSum;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
	

}
