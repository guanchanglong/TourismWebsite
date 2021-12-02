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
        for (User user:list){
            if (user.getUsername()==null||user.getUsername().isEmpty()){
                user.setUsername("暂无");
            }
            if (user.getPhone()==null||user.getPhone().isEmpty()){
                user.setPhone("暂无");
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public void addCommonUser(User user){
        userDao.addCommonUser(user);
    }

    @Override
    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(int userId){
        return userDao.findById(userId);
    }

    @Override
    public void updateUser(User user){
        userDao.updateUser(user);
    }

    /**
     * 管理员改变用户账号状态为被封号
     * @param userId
     */
    @Override
    public void updateUserStatusToBad(int userId){
        userDao.updateUserStatusToBad(userId);
    }

    /**
     * 管理员改变用户账号为正常
     * @param userId
     */
    @Override
    public void updateUserStatusToGood(int userId){
        userDao.updateUserStatusToGood(userId);
    }

    @Override
    public PageInfo<User> findCommonByUserName(int pageNum,
                                               int size,
                                               String username){
        PageHelper.startPage(pageNum,size);
        List<User> list = userDao.findCommonByUserName("%"+username+"%");
        return new PageInfo<>(list);
    }
}
