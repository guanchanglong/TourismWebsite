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
    //酒店简介(不超过30个字)
    private String info;
    //展示价格
    private int priceToShow;
    //联系方式
    private String phone;
    //基本信息
    private String basicInformation;
    //酒店详细介绍
    private String infoDetail;
    //膳食安排
    private String eatDetail;
}
