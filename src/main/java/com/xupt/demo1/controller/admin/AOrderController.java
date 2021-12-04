package com.xupt.demo1.controller.admin;

import com.xupt.demo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Controller
@RequestMapping("/admin/order")
public class AOrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("orderId") int orderId,
                              @RequestParam("userId") int userId){
        orderService.deleteOrder(orderId);
        return "redirect:/admin/page/toUserOrderPage?userId=" + userId;
    }



}
