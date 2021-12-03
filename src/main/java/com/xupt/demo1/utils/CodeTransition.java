package com.xupt.demo1.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author 小关同学
 * @create 2021/11/7
 * 中文转URL
 */
public class CodeTransition {

    public static String stringToASCII(String param) {//字符串转换为ASCII码
        String result = "";
        try {
            result = URLEncoder.encode(param,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(stringToASCII("西安"));
    }
}