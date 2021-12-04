package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/11/1
 * 订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    //主键
    private int id;
    //订单号
    private String orderId;
    //用户Id
    private int userId;
    //商品Id
    private int goodsId;
    //商品类型(0是门票，1是酒店预订)
    private int goodsType;
    //下单日期
    private Date time;
    //订单状态(1已支付，2退款)
    private int status;
    //是否在客户端显示(0是不显示，1是显示，默认为1)
    private int showToUser;
    //金额
    private double price;
    //名称
    private String goodsName;
    //图片
    private String picture;
    //对外展示的时间
    private String timeToShow;
    //用户名
    private String username;
    //用户头像
    private String userPicture;
    //用户邮箱
    private String userEmail;

}
//下单日期
//消费内容
    //名称+图片
//消费金额
//删除订单
//
