package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小关同学
 * @create 2021/11/1
 * 景点
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spot {
    private int id;
    //景点名
    private String name;
    //地点
    private String area;
    //简介
    private String info;
    //封面图片地址
    private String picture;
    //价格
    private double price;
    //详细介绍
    private String infoDetail;
    //开放时间
    private String openTime;
    //景点详情页面对应的页面id，为后续继续爬数据用
    private String spotWebId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spot spot = (Spot) o;

        if (Double.compare(spot.price, price) != 0) return false;
        if (name != null ? !name.equals(spot.name) : spot.name != null) return false;
        if (area != null ? !area.equals(spot.area) : spot.area != null) return false;
        if (info != null ? !info.equals(spot.info) : spot.info != null) return false;
        if (picture != null ? !picture.equals(spot.picture) : spot.picture != null) return false;
        if (infoDetail != null ? !infoDetail.equals(spot.infoDetail) : spot.infoDetail != null) return false;
        if (openTime != null ? !openTime.equals(spot.openTime) : spot.openTime != null) return false;
        return spotWebId != null ? spotWebId.equals(spot.spotWebId) : spot.spotWebId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (infoDetail != null ? infoDetail.hashCode() : 0);
        result = 31 * result + (openTime != null ? openTime.hashCode() : 0);
        result = 31 * result + (spotWebId != null ? spotWebId.hashCode() : 0);
        return result;
    }
}