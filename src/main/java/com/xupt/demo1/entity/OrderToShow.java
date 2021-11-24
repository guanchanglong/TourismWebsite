package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderToShow {
    private String goodsName;
    private String picture;
    private double price;
    private Date time;
}
