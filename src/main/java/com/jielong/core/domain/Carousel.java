package com.jielong.core.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Carousel {
    private Integer id;

    private String carouseladdress;

    private String type;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarouseladdress() {
        return carouseladdress;
    }

    public void setCarouseladdress(String carouseladdress) {
        this.carouseladdress = carouseladdress == null ? null : carouseladdress.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}