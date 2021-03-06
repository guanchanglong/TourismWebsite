package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小关同学
 * @create 2021/11/7
 * 景点详细信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotDetail {

    //主键
    private int id;
    //标题
    private String title;
    //每个标题的简介
    private String info;
    //图片
    private String picture;
}
