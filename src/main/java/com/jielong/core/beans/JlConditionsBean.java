package com.jielong.core.beans;

public class JlConditionsBean {
	private String topic;
	private String goodsName;
	private String userNickName;

	public String getTopic() {
		return topic;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	@Override
	public String toString() {
		return "JlConditionsBean [topic=" + topic + ", goodsName=" + goodsName + ", userNickName=" + userNickName + "]";
	}
	
	
	

}
