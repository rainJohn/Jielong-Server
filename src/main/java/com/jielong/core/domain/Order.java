package com.jielong.core.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Order {
    private Integer id;

    private String orderNum;

    private Integer jielongId;
    
    private String jielongTopic;

    private Integer userId;

    private Integer addressId;

    private String userName;

    private String userPhone;

    private BigDecimal sumMoney;

    private Integer state;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    
    private List<OrderGoods> orderGoods;
    
    private UserInfo userInfo;
    
    private UserAddress userAddress;
    
    private Integer isSetGroup;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJielongTopic() {
		return jielongTopic;
	}

	public void setJielongTopic(String jielongTopic) {
		this.jielongTopic = jielongTopic;
	}

	public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public Integer getJielongId() {
        return jielongId;
    }

    public void setJielongId(Integer jielongId) {
        this.jielongId = jielongId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public List<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public Integer getIsSetGroup() {
		return isSetGroup;
	}

	public void setIsSetGroup(Integer isSetGroup) {
		this.isSetGroup = isSetGroup;
	}


    
    
}