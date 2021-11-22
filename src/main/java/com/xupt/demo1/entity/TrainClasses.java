package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小关同学
 * @create 2021/11/17
 * 列车班次
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainClasses {
    //这个，暂定
    private int id;
    //车次
    private String trainId;
    //出发站
    private String startStation;
    //终点站
    private String endStation;
    //出发时间
    private String startTime;
    //到达时间
    private String endTime;
    //总耗时
    private String totalTime;
    //软卧
    private String softSleeper;
    //无座
    private String withoutSeat;
    //硬卧
    private String hardSleeper;
    //硬座
    private String hardSeat;
    //一等座
    private String firstClass;
    //二等座
    private String secondClass;
    //特等座
    private String premierClass;
}
