package com.jielong.core.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class OrderGroupConsole {
	
    //
    private Integer jielongId;
    //shangpin_ID
    private Integer goodsId;

    //成团状态 1为成团，0为未成团
    private int groupOKFlg;
    
    //接龙状态 1为关闭，0为开启
    private int consoleFlg;
    
    private Date createdAt;

    private Date updatedAt;
    
	

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

	public int getGroupOKFlg() {
		return groupOKFlg;
	}

	public void setGroupOKFlg(int groupOKFlg) {
		this.groupOKFlg = groupOKFlg;
	}

	public int getConsoleFlg() {
		return consoleFlg;
	}

	public void setConsoleFlg(int consoleFlg) {
		this.consoleFlg = consoleFlg;
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

}