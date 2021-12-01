package com.xupt.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.demo1.dao.UserDao;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/1
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String email, String password) {
        return userDao.findByEmailAndPassword(email,password);
    }

    @Override
    public User adminLogin(String phone, String password) {
        return userDao.findByEmailAndPasswordAndAdminRole(phone,password);
    }

    @Override
    public void register(String email, String password) {
        userDao.insertUser(email,password);
    }

    @Override
    public PageInfo<User> findAllCommonUser(int pageNum,int size){
        PageHelper.startPage(pageNum, size);
        List<User> list = userDao.findAllCommonUser();
        return new PageInfo<>(list);
    }
}
