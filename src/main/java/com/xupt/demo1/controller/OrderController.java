package com.xupt.demo1.controller;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Order;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.OrderService;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/11/17
 * 订单支付接口集合
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户支付商品订单
     * @param model
     * @param session
     * @param payWay
     * @param goodsType
     * @param goodsId
     * @return
     */
    @GetMapping("/pay")
    public String pay(Model model,
                      HttpSession session,
                      @RequestParam(value = "payWay") int payWay,
                      @RequestParam(value = "goodsType")int goodsType,
                      @RequestParam(value = "goodsId")int goodsId){
        Order order = new Order();
        //订单编号
        order.setOrderId("");
        //设置用户Id
        User user = (User) session.getAttribute("user");
        order.setUserId(user.getId());
        //设置商品类型(0是景点门票，1是酒店预订)
        order.setGoodsType(goodsType);
        //设置商品id
        order.setGoodsId(goodsId);
        //设置订单支付方式
        order.setPayWay(payWay);
        //设置支付状态(已支付)
        order.setStatus(1);
        //设置支付时间
        order.setTime(new Date());
        orderService.addOrder(order);
        model.addAttribute("message","支付成功");
        return "";
    }

    /**
     * 用户查看订单
     * @param model
     * @param session
     * @param pageNum
     * @param size
     * @return
     */
    @GetMapping("/showOrder")
    public String showOrder(Model model,
                            HttpSession session,
                            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                            @RequestParam(value = "size",defaultValue = "10")int size){
        User user = (User)session.getAttribute("user");
        PageInfo<Order> pageInfo = orderService.findOrderByUserId(user.getId(),pageNum,size);
        model.addAttribute("page",pageInfo);
        return "";
    }

    /**
     * 用户删除订单记录，只是将订单状态设置为不显示而已，并不是真正在数据库删除
     * @param model
     * @param orderId
     * @return
     */
    @GetMapping("/deleteOrderRecord")
    public String deleteOrderRecord(Model model,
                                    @RequestParam int orderId){
        orderService.updateOrderShowToUser(orderId);
        model.addAttribute("message","删除成功");
        return "";
    }

}
