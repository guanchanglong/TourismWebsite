package com.xupt.demo1.dao;

import com.xupt.demo1.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Mapper
public interface OrderDao {

    void addOrder(@Param("order")Order order);

    List<Order> findOrderByUserIdAndShowToUser(@Param("userId") int userId);

    void updateOrderShowToUser(@Param("orderId")int orderId);

    List<Order> findByUserId(@Param("userId") int userId);

    void deleteOrder(@Param("orderId") int orderId);

    List<Order> findAll();
}
