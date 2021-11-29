package com.xupt.demo1.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 小关同学
 * @create 2021/11/29
 */
@Controller
@RequestMapping("/admin/page")
public class APageController {

    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
        return "admin/login";
    }
}
