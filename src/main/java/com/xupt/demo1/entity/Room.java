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
public class Room {
    private int id;
    //房间标题
    private String title;
    //房间图片
    private String picture;
    //房间简介
    private String info;
    //房间类型
    private String bedType;
    //房间价格
    private int price;
    //早餐详情
    private String breakfast;
}
