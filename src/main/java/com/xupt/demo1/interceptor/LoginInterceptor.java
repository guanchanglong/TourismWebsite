package com.xupt.demo1.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 小关同学
 * @create 2021/11/1
 */
//现在已经不使用HandlerInterceptorAdapter了，因为他是一个过时的方法
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception{
        if (request.getSession().getAttribute("adminUser")==null){
            //在未登录的情况下拦截通往admin下的所有请求
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
