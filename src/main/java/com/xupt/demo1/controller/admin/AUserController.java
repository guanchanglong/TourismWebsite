package com.xupt.demo1.controller.admin;

import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 小关同学
 * @create 2021/11/1
 * 管理员登录 和 管理用户行为
 */
@CrossOrigin
@Controller
@RequestMapping("/admin/user")
public class AUserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.adminLogin(email,password);
        if (user!=null){
            //将密码清空再存放到session中
            user.setPassword(null);
            session.setAttribute("adminUser",user);
            //设置session一个小时后就过期
            session.setMaxInactiveInterval(3600);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","邮箱或密码错误");
            return "redirect:/admin/page/toLoginPage";
        }
    }

    /**
     * 退出系统
     * @param session
     * @return
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        //清空session里面的数据
        session.removeAttribute("adminUser");
        return "";
    }
}
