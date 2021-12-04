package com.xupt.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.demo1.dao.OrderDao;
import com.xupt.demo1.dao.UserDao;
import com.xupt.demo1.entity.Order;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private UserDao userDao;

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
    public PageInfo<Order> findByUserId(int userId,int pageNum,int size){
        //按照时间排序
        PageHelper.startPage(pageNum,size,"time");
        List<Order> list = orderDao.findByUserId(userId);
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

    @Override
    public void deleteOrder(int orderId){
        orderDao.deleteOrder(orderId);
    }

    @Override
    public PageInfo<Order> findAll(int pageNum,int size){
        PageHelper.startPage(pageNum, size);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        List<Order> list = orderDao.findAll();
        for (Order order:list){
            User user = userDao.findById(order.getUserId());
            if (user.getUsername()==null||user.getUsername().isEmpty()){
                order.setUsername("暂无");
            }else{
                order.setUsername(user.getUsername());
            }
            if (user.getPicture()==null||user.getPicture().isEmpty()){
                order.setUserPicture(null);
            }else{
                order.setUserPicture(user.getPicture());
            }
            order.setUserEmail(user.getEmail());
            order.setTimeToShow(format.format(order.getTime()));
        }
        return new PageInfo<>(list);
    }

    /**
     * 返回月季度图表统计数据信息
     * @return
     */
    @Override
    public List<Double> returnChartData(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        List<Order> list = orderDao.findAll();
        Double[] months = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        for (Order order:list){
            String date = format.format(order.getTime());
            int month = Integer.parseInt(date.substring(5,7));
            switch (month){
                case 1:
                    months[0] += order.getPrice();
                    break;
                case 2:
                    months[1] += order.getPrice();
                    break;
                case 3:
                    months[2] += order.getPrice();
                    break;
                case 4:
                    months[3] += order.getPrice();
                    break;
                case 5:
                    months[4] += order.getPrice();
                    break;
                case 6:
                    months[5] += order.getPrice();
                    break;
                case 7:
                    months[6] += order.getPrice();
                    break;
                case 8:
                    months[7] += order.getPrice();
                    break;
                case 9:
                    months[8] += order.getPrice();
                    break;
                case 10:
                    months[9] += order.getPrice();
                    break;
                case 11:
                    months[10] += order.getPrice();
                    break;
                case 12:
                    months[11] += order.getPrice();
                    break;
            }
        }
        return new ArrayList<>(Arrays.asList(months));
    }

    @Override
    public Double[] statisticsIncome() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date dateNow = new Date();
        String today = format.format(dateNow);
        //今年
        int thisYear = Integer.parseInt(today.substring(0,4));
        //本月
        int thisMonth = Integer.parseInt(today.substring(5,7));
        //今天
        int thisDay = Integer.parseInt(today.substring(8,10));
        List<Order> list = orderDao.findAll();
        //months[0]是本月收入，months[1]是今日收入，months[2]是总收入，months[3]是订单总数
        Double[] months = new Double[]{0.0, 0.0, 0.0,0.0};

        for (Order order : list) {
            String date = format.format(order.getTime());
            //年份
            int year = Integer.parseInt(date.substring(0,4));
            //月份
            int month = Integer.parseInt(date.substring(5, 7));
            //日期
            int day = Integer.parseInt(date.substring(8,10));

            //今年
            if (year==thisYear){
                //本月
                if (month==thisMonth){
                    months[0] += order.getPrice();
                    //今天
                    if (day==thisDay){
                        months[1] += order.getPrice();
                    }
                }
            }

            months[2] += order.getPrice();

        }

        months[3] = Double.valueOf(list.size());

        return months;
    }
}
