package com.xupt.demo1.utils;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 小关同学
 * @create 2021/11/9
 */
public class Request {

    public static String doGet(String url) {
        // 输入流
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        // 创建httpClient实例
        HttpClient httpClient = new HttpClient();
        // 设置http连接主机服务超时时间：60000毫秒
        // 先获取连接管理器对象，再获取参数对象,再进行参数的赋值
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        // 创建一个Get方法实例对象
        GetMethod getMethod = new GetMethod(url);
        // 设置get请求超时为60000毫秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        // 设置请求重试机制，默认重试次数：3次，参数设置为true，重试机制可用，false相反
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));

        DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);

        try {

            Thread.sleep(5000);
            // 执行Get方法
            int statusCode = httpClient.executeMethod(getMethod);
            System.out.println("请求状态码："+statusCode);
            // 判断返回码
//            if (statusCode != HttpStatus.SC_OK) {
            // 如果状态码返回的不是ok,说明失败了,打印错误信息
//                System.err.println("Method faild: " + getMethod.getStatusLine());
//            } else {


            // 通过getMethod实例，获取远程的一个输入流
            is = getMethod.getResponseBodyAsStream();
            // 包装输入流
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuffer sbf = new StringBuffer();
            // 读取封装的输入流
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sbf.append(temp).append("\r\n");
            }
            result = sbf.toString();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 释放连接
            getMethod.releaseConnection();
        }
        return result;

    }
}
