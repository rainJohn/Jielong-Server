package com.jielong.core.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderGroup {
	//ID
    private Integer id;
    //购买者ID
    private Integer custId;
  //接龙_ID
    private Integer jielongId;
  //订单ID
    private String orderId;
  //商品_ID
    private Integer goodsId;
  //购买者用户名
    private String custName;
  //购买者用户电话
    private String custPhone;
  //购买者用户备注
    private String custNote;
  //购买商品数量
    private Integer custBuyNum;
  //购买商品单价
    private BigDecimal custBuyPrice;
  //购买商品金额
    private BigDecimal custBuyAllMoney;
  //交易状态 0为已下单，1为正在交易，2为交易正常完成，3为退货，4为其他异常
    private Integer tradeFlg;
  //订单状态 1为关闭订单，0为订单正常交易，2为交易完成
    private Integer orderFlg;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")    
    private Date createdAt;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    
    //提货地址
    private Integer addressId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getJielongId() {
        return jielongId;
    }

    public void setJielongId(Integer jielongId) {
        this.jielongId = jielongId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone == null ? null : custPhone.trim();
    }

    public String getCustNote() {
        return custNote;
    }

    public void setCustNote(String custNote) {
        this.custNote = custNote == null ? null : custNote.trim();
    }

    public Integer getCustBuyNum() {
        return custBuyNum;
    }

    public void setCustBuyNum(Integer custBuyNum) {
        this.custBuyNum = custBuyNum;
    }

    public BigDecimal getCustBuyPrice() {
        return custBuyPrice;
    }

    public void setCustBuyPrice(BigDecimal custBuyPrice) {
        this.custBuyPrice = custBuyPrice;
    }

    public BigDecimal getCustBuyAllMoney() {
        return custBuyAllMoney;
    }

    public void setCustBuyAllMoney(BigDecimal custBuyAllMoney) {
        this.custBuyAllMoney = custBuyAllMoney;
    }

    public Integer getTradeFlg() {
        return tradeFlg;
    }

    public void setTradeFlg(Integer tradeFlg) {
        this.tradeFlg = tradeFlg;
    }

    public Integer getOrderFlg() {
        return orderFlg;
    }

    public void setOrderFlg(Integer orderFlg) {
        this.orderFlg = orderFlg;
    }

    public String getCreatedAt() {
    	TimeZone timeZone = TimeZone.getDefault();  
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		simpleDateFormat.setTimeZone(timeZone);  
		if(createdAt!=null) {
		  return simpleDateFormat.format(createdAt);
		}else {
			return null;
		}   
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
    	TimeZone timeZone = TimeZone.getDefault();  
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		simpleDateFormat.setTimeZone(timeZone);  
		if(updatedAt!=null) {
		  return simpleDateFormat.format(updatedAt);
		}else {
			return null;
		}   
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
    
    
}