package com.jielong.core.domain;

public class Carousel {
    private Integer id;

    private String carouseladdress;

    private String type;

    private String createtime;

    private String updatetime;

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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }
}
