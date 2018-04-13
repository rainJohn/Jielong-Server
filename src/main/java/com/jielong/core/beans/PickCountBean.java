package com.jielong.core.beans;

import java.math.BigDecimal;
import java.util.List;

import com.jielong.core.domain.Goods;

public class PickCountBean {
	private Goods goods;
	//参与金龙人数
	private Integer joinPeopleSum;
	//已售数量
	private Integer sellSum;
	//入账总额
	private BigDecimal moneySum;
	//购买列表
	private List<PickBean> pickBeans;

	public Goods getGoods() {
		return goods;
	}

	public Integer getJoinPeopleSum() {
		return joinPeopleSum;
	}

	public Integer getSellSum() {
		return sellSum;
	}

	public BigDecimal getMoneySum() {
		return moneySum;
	}

	public List<PickBean> getPickBeans() {
		return pickBeans;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public void setJoinPeopleSum(Integer joinPeopleSum) {
		this.joinPeopleSum = joinPeopleSum;
	}

	public void setSellSum(Integer sellSum) {
		this.sellSum = sellSum;
	}

	public void setMoneySum(BigDecimal moneySum) {
		this.moneySum = moneySum;
	}

	public void setPickBeans(List<PickBean> pickBeans) {
		this.pickBeans = pickBeans;
	}
	
	
}
