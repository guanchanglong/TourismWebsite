package com.xupt.demo1.dao;

import com.xupt.demo1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 小关同学
 * @create 2021/11/1
 */
@Mapper
public interface UserDao {

    User findByEmailAndPassword(@Param("email")String email,@Param("password")String password);

    User findByEmailAndPasswordAndRole(@Param("email")String email,@Param("password")String password);

    void insertUser(@Param("email") String email,@Param("password") String password);


}
