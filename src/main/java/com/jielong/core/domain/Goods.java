package com.jielong.core.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Goods {
    private Integer id;

    private Integer jielongId;

    private String name;

    private Integer parentClassId;

    private Integer subClassId;

    private String specification;

    private BigDecimal price;

    private Integer repertory;

    private Integer isSetGroup;

    private String groupSum;
    
    private Integer remainSum; 

    private String serverPaths;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentClassId() {
        return parentClassId;
    }

    public void setParentClassId(Integer parentClassId) {
        this.parentClassId = parentClassId;
    }

    public Integer getSubClassId() {
        return subClassId;
    }

    public void setSubClassId(Integer subClassId) {
        this.subClassId = subClassId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getRepertory() {
        return repertory;
    }

    public void setRepertory(Integer repertory) {
        this.repertory = repertory;
    }

    public Integer getIsSetGroup() {
        return isSetGroup;
    }

    public void setIsSetGroup(Integer isSetGroup) {
        this.isSetGroup = isSetGroup;
    }

    public String getGroupSum() {
        return groupSum;
    }

    public void setGroupSum(String groupSum) {
        this.groupSum = groupSum == null ? null : groupSum.trim();
    }

    public String getServerPaths() {
        return serverPaths;
    }

    public void setServerPaths(String serverPaths) {
        this.serverPaths = serverPaths == null ? null : serverPaths.trim();
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

	public Integer getJielongId() {
		return jielongId;
	}

	public void setJielongId(Integer jielongId) {
		this.jielongId = jielongId;
	}

	public Integer getRemainSum() {
		return remainSum;
	}

	public void setRemainSum(Integer remainSum) {
		this.remainSum = remainSum;
	}
	
    
}