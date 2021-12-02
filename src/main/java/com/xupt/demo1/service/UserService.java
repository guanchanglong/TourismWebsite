package com.xupt.demo1.service;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.User;

/**
 * @author 小关同学
 * @create 2021/11/1
 */
public interface UserService {

    User login(String phone,String password);

    User adminLogin(String phone,String password);

    void register(String email,String password);

    PageInfo<User> findAllCommonUser(int pageNum, int size);

    void addCommonUser(User user);

    User findByEmail(String email);

    User findById(int userId);

    void updateUser(User user);

    void updateUserStatusToBad(int userId);

    void updateUserStatusToGood(int userId);

    PageInfo<User> findCommonByUserName(int pageNum, int size, String username);
}
