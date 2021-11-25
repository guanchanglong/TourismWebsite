package com.xupt.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.demo1.dao.OrderDao;
import com.xupt.demo1.entity.Order;
import com.xupt.demo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/18
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public void addOrder(Order order){
        orderDao.addOrder(order);
    }

    @Override
    public PageInfo<Order> findOrderByUserId(int userId,int pageNum,int size){
        //按照时间排序
        PageHelper.startPage(pageNum,size,"time");
        List<Order> list = orderDao.findOrderByUserIdAndShowToUser(userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        for (Order order:list){
            String time = format.format(order.getTime());
            order.setTimeToShow(time);
        }
        return new PageInfo<>(list);
    }

    @Override
    public void updateOrderShowToUser(int orderId){
        orderDao.updateOrderShowToUser(orderId);
    }
}
