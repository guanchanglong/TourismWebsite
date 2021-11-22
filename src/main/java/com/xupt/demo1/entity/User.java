package com.xupt.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 小关同学
 * @create 2021/11/1
 * 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    //电话
    private String phone;
    //邮箱
    private String email;
    //密码
    private String password;
    //用户名(如果有)
    private String username;
    //角色权限(0是管理员权限，1是普通用户权限)
    private int role;

}
