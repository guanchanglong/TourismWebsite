package com.xupt.demo1.controller;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Order;
import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.OrderService;
import com.xupt.demo1.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/11/17
 * 订单支付接口集合
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SpotService spotService;

    /**
     * 用户支付商品订单
     * @param model
     * @param session
     * @param goodsType
     * @param goodsId
     * @return
     */
    @PostMapping("/pay")
    public String pay(Model model,
                      HttpSession session,
                      @RequestParam(value = "goodsType")int goodsType,
                      @RequestParam(value = "goodsId")int goodsId){
        Order order = new Order();
        Date today = new Date();
        //制定日期格式
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        User user = (User) session.getAttribute("user");
        //设置用户Id
        order.setUserId(user.getId());
        //生成订单编号
        String orderId = "#"+format.format(today)+"T"+goodsType+"I"+goodsId+"U"+user.getId();
        order.setOrderId(orderId);
        //设置商品类型(0是景点门票，1是酒店预订)
        order.setGoodsType(goodsType);
        //设置商品id
        order.setGoodsId(goodsId);
        //设置支付状态(已支付)
        order.setStatus(1);
        //设置支付时间
        order.setTime(today);

        if (goodsType==0){
            Spot spot = spotService.findSpotById(goodsId);
            //商品名称
            order.setGoodsName(spot.getName());
            //商品价格
            order.setPrice(spot.getPrice());
            //商品图片
            order.setPicture(spot.getPicture());
        }

        orderService.addOrder(order);
        model.addAttribute("user",user);
        return "user/paySuccess";
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
        System.out.println();
        model.addAttribute("page",pageInfo);
        model.addAttribute("user",user);
        return "user/order";
    }

    /**
     * 用户删除订单记录，只是将订单状态设置为不显示而已，并不是真正在数据库删除
     * @return
     */
    @GetMapping("/deleteOrderRecord")
    public String deleteOrderRecord(@RequestParam(value = "orderId") int orderId){
        orderService.updateOrderShowToUser(orderId);
        return "redirect:../spot/showOrder";
    }

}
