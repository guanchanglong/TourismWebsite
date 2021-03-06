package com.xupt.demo1.service;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Order;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/18
 */
public interface OrderService {
    void addOrder(Order order);

    PageInfo<Order> findOrderByUserId(int userId, int pageNum, int size);

    void updateOrderShowToUser(int orderId);

    PageInfo<Order> findByUserId(int userId,int pageNum,int size);

    void deleteOrder(int orderId);

    List<Double> returnChartData();

    Double[] statisticsIncome();

    PageInfo<Order> findAll(int pageNum,int size);
}
