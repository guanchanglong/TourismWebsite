package com.xupt.demo1.controller.admin;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Order;
import com.xupt.demo1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Controller
@RequestMapping("/admin/order")
public class AOrderController {

    @Autowired
    private OrderService orderService;

}
