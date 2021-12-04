package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    //主键id
    private int id;
    //图片
    private String picture;
    //酒店名称
    private String name;
    //酒店简介
    private String info;
    //展示价格
    private String address;
    //联系方式
    private String phone;
    //酒店类型
    private String type;
    //营业还是停业
    private int status;
    //最低价格
    private String minPrice;
}
