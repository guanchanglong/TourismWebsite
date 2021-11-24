package com.xupt.demo1.service;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Order;
import com.xupt.demo1.entity.OrderToShow;

/**
 * @author 小关同学
 * @create 2021/11/18
 */
public interface OrderService {
    void addOrder(Order order);

    PageInfo<OrderToShow> findOrderByUserId(int userId, int pageNum, int size);

    void updateOrderShowToUser(int orderId);
}
