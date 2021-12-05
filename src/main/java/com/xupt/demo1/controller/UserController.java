package com.xupt.demo1.controller;

import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.UserService;
import com.xupt.demo1.utils.EmailInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/11/2
 */
@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
        return "user/login";
    }

    @RequestMapping("/toRegisterPage")
    public String toRegisterPage(){
        return "user/register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.login(email,password);
        if (user!=null){
            //账号被封禁
            if (user.getRole()==2){
                attributes.addFlashAttribute("message","该账号涉嫌违规");
                return "redirect:/user/toLoginPage";
            }
            user.setPassword(null);
            session.setAttribute("user",user);
            //设置用户的session在3个小时后过期
            session.setMaxInactiveInterval(3600*3);
            return "redirect:/page/index";
        }else{
            attributes.addFlashAttribute("message","邮箱或密码错误");
            return "redirect:/user/toLoginPage";
        }
    }

    /**
     * 获取邮箱，然后发送随机验证码到相应的邮箱
     * @param targetEmail 目标邮箱
     * @return
     */
    @GetMapping("/sendEmail")
    public String sendCode(@RequestParam(value = "targetEmail") String targetEmail,
                           HttpSession session){

        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("西安旅游网 - 验证码");

        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("506921079@qq.com");

        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        message.setTo(targetEmail);

        // 设置邮件发送日期
        message.setSentDate(new Date());

        //生成随机6位验证码
        String code = EmailInfoUtil.createRandCode().toString();

        System.out.println("存入session的验证码："+code);
        System.out.println("code" + targetEmail);

        //将验证码存入session域中
        session.setAttribute("code" + targetEmail ,code);

        //设置session在90s后就过期
        session.setMaxInactiveInterval(90);

        String content = "尊敬的用户：\n" + "\n" + "您好！\n" + "\n" + "邮箱验证码：\n" + "\n" + code + "\n" + "\n" + "系统发信，请勿回复\n" + "\n" + "网站运营团队";

        // 设置邮件的正文
        message.setText(content);

        // 发送邮件
        mailSender.send(message);

        System.out.println("发送成功");

        return "user/register";

    }

    /**
     * 用户注册
     * @param session
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String code,
                           HttpSession session,
                           RedirectAttributes attributes){
        String trueCode = (String) session.getAttribute("code" + email);

        if (!password.equals(confirmPassword)){
            attributes.addFlashAttribute("message","两次输入的密码不一致");
            return "redirect:/user/toRegisterPage";
        }

        //判断邮箱是否已被注册
        if(userService.findByEmail(email)!=null){
            attributes.addFlashAttribute("message","该邮箱已被注册");
            return "redirect:/user/toRegisterPage";
        }else{
            //判断用户输入的code跟我们发送到邮箱的code是否一致
            if (!code.equals(trueCode)){
                attributes.addFlashAttribute("message","验证码有误");
                return "redirect:/user/toRegisterPage";
            }else{
                //保存用户的注册信息
                userService.register(email, password);
                //移除用户保存在session的验证码，释放内存
                session.removeAttribute("code" + email);
                return "redirect:/user/toLoginPage";
            }
        }
    }

    @PostMapping("/findBackThePassword")
    public String findBackThePassword(@RequestParam(value = "email") String email,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "confirmPassword") String confirmPassword,
                                      @RequestParam(value = "code") String code,
                                      HttpSession session,
                                      RedirectAttributes attributes){
        String trueCode = (String) session.getAttribute("code" + email);
        if (!password.equals(confirmPassword)){
            attributes.addFlashAttribute("message","两次输入的密码不一致");
            return "redirect:/user/toRegisterPage";
        }

        //判断用户输入的code跟我们发送到邮箱的code是否一致
        if (!code.equals(trueCode)){
            attributes.addFlashAttribute("message","验证码有误");
            return "redirect:/user/toRegisterPage";
        }else{
            //更新用户的注册信息
            User user = userService.findByEmail(email);
            user.setPassword(password);
            userService.updateUser(user);
            //移除用户保存在session的验证码，释放内存
            session.removeAttribute("code" + email);
            return "redirect:/user/toLoginPage";
        }
    }

    @PostMapping("/changeUserData")
    public String changeUserData(HttpSession session,
                                 RedirectAttributes attributes,
                                 @RequestParam(value = "email") String email,
                                 @RequestParam(value = "phone") String phone,
                                 @RequestParam(value = "username") String username,
                                 @RequestParam(value = "picture") String picture,
                                 @RequestParam(value = "password") String password){
        User user = (User) session.getAttribute("user");
        User toConfirm = userService.findByEmail(email);
        if (toConfirm!=null && user.getId()!=toConfirm.getId()){
            attributes.addFlashAttribute("message","该邮箱已被注册");
            return "redirect:/page/toModifyUserDataPage";
        }
        user.setEmail(email);
        user.setPhone(phone);
        user.setUsername(username);
        user.setPicture(picture);
        user.setPassword(password);
        userService.updateUser(user);
        attributes.addFlashAttribute("message","信息修改成功");
        return "redirect:/page/toModifyUserDataPage";
    }

    /**
     * 退出系统
     * @param session
     * @return
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        //清空session里面的数据
        session.removeAttribute("user");
        return "redirect:/page/index";
    }
}