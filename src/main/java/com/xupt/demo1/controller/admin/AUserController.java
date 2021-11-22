package com.xupt.demo1.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

/**
 * @author 小关同学
 * @create 2021/11/1
 * 管理员登录 和 管理用户行为
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/user")
public class AUserController {

    @Autowired
    private UserService userService;

    /**
     * 管理员登录
     * @param param
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestBody JSONObject param,
                                    HttpSession session){
        String email = param.getString("email");
        String password = param.getString("password");
        User user = userService.adminLogin(email,password);
        if (user!=null){
            //将密码清空再存放到session中
            user.setPassword(null);
            session.setAttribute("adminUser",user);
            //设置session一个小时后就过期
            session.setMaxInactiveInterval(3600);
            return "";
        }else{
            return "";
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
