package com.xupt.demo1.utils;

/**
 * @author 小关同学
 * @create 2021/11/2
 */
public class EmailInfoUtil {

    /**
     * 生成随机6位验证码
     * @return 返回验证码
     */
    public static StringBuilder createRandCode(){
        StringBuilder code = new StringBuilder();
        for (int i = 0;i < 6;i++){
            code.append((int)(Math.random()*9));
        }
        return code;
    }

    public static void main(String[] args) {
        String code = EmailInfoUtil.createRandCode().toString();
        System.out.println(code);
    }
}
