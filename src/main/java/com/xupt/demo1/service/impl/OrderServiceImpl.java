package com.xupt.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.demo1.dao.HotelDao;
import com.xupt.demo1.dao.OrderDao;
import com.xupt.demo1.dao.SpotDao;
import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.entity.Order;
import com.xupt.demo1.entity.OrderToShow;
import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/18
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SpotDao spotDao;

    @Autowired
    private HotelDao hotelDao;

    @Override
    public void addOrder(Order order){
        orderDao.addOrder(order);
    }

    @Override
    public PageInfo<OrderToShow> findOrderByUserId(int userId,int pageNum,int size){
        //按照时间排序
        PageHelper.startPage(pageNum,size,"time");
        List<Order> list = orderDao.findOrderByUserIdAndShowToUser(userId);
        List<OrderToShow> result = new ArrayList<>();
        for (Order order:list){
            OrderToShow orderToShow = new OrderToShow();
            orderToShow.setTime(order.getTime());
            if (order.getGoodsType()==0){
                Spot spot = spotDao.findSpotById(order.getGoodsId());
                orderToShow.setPrice(spot.getPrice());
                orderToShow.setPicture(spot.getPicture());
                orderToShow.setGoodsName(spot.getName());
            }
            if (order.getGoodsType()==1){
                Hotel hotel = hotelDao.findAllById(order.getGoodsId());
            }
            result.add(orderToShow);
        }
        return new PageInfo<>(result);
    }

    @Override
    public void updateOrderShowToUser(int orderId){
        orderDao.updateOrderShowToUser(orderId);
    }
}
