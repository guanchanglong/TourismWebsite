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
            return "redirect:/admin/page/toIndexPage";
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
        return "redirect:/admin/page/toLoginPage";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam(value = "username") String username,
                          @RequestParam(value = "userEmail") String userEmail,
                          @RequestParam(value = "userPhone") String userPhone,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "picture") String picture,
                          RedirectAttributes attributes){
        //判断该邮箱是否被注册
        if (userService.findByEmail(userEmail)!=null){
            attributes.addFlashAttribute("message","该邮箱已被注册");
            return "redirect:/admin/page/toUserAddPage";
        }

        User user = new User();
        user.setUsername(username);
        user.setPicture(picture);
        user.setEmail(userEmail);
        user.setPhone(userPhone);
        user.setPassword(password);
        userService.addCommonUser(user);
        attributes.addFlashAttribute("message","添加成功");
        return "redirect:/admin/page/toUserAddPage";
    }

    @PostMapping("/updateUser/{userId}")
    public String updateUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "userEmail") String userEmail,
                             @RequestParam(value = "userPhone") String userPhone,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "picture") String picture,
                             @PathVariable("userId") int userId,
                             RedirectAttributes attributes){
        if (userEmail==null||userEmail.isEmpty()){
            attributes.addFlashAttribute("message","用户邮箱不能为空");
            return "redirect:/admin/page/toUserModifyPage?userId=" + userId;
        }
        if (password==null||password.isEmpty()){
            attributes.addFlashAttribute("message","密码不能为空");
            return "redirect:/admin/page/toUserModifyPage?userId=" + userId;
        }

        User user = new User();
        user.setId(userId);
        user.setEmail(userEmail);
        user.setPassword(password);
        if (username==null||username.isEmpty()){
            user.setUsername(null);
        }else{
            user.setUsername(username);
        }
        if (userPhone==null||userPhone.isEmpty()){
            user.setPhone(null);
        }else{
            user.setPhone(userPhone);
        }
        if (picture==null||picture.isEmpty()){
            user.setPicture(null);
        }else{
            user.setPicture(picture);
        }
        userService.updateUser(user);
        attributes.addFlashAttribute("message","修改成功");
        return "redirect:/admin/page/toUserModifyPage?userId=" + userId;
    }

    @RequestMapping("/changeUserStatusToBad")
    public String changeUserStatusToBad(@RequestParam(value = "userId") int userId){
        userService.updateUserStatusToBad(userId);
        return "redirect:/admin/page/toUsersPage";
    }

    @RequestMapping("/changeUserStatusToGood")
    public String changeUserStatusToGood(@RequestParam(value = "userId") int userId){
        userService.updateUserStatusToGood(userId);
        return "redirect:/admin/page/toUsersPage";
    }
}
