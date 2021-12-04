package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/12/3
 * 用户投诉
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {
    private int id;
    //投诉时间
    private Date time;
    //展示的时间
    private String timeToShow;
    //投诉人邮箱
    private String email;
    //投诉内容
    private String content;
    //投诉状态，0是未处理，1是已处理
    private int status;
}
