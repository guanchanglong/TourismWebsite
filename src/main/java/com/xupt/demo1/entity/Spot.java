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




}
//http://piao.qunar.com/ticket/detail_1313759051.html?st=澳门环岛游#from=mpl_search_suggest
//