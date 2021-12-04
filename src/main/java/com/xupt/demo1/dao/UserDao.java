package com.xupt.demo1.dao;

import com.xupt.demo1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/1
 */
@Mapper
public interface UserDao {

    User findByEmailAndPassword(@Param("email")String email,@Param("password")String password);

    User findByEmailAndPasswordAndAdminRole(@Param("email")String email,@Param("password")String password);

    void insertUser(@Param("email") String email,@Param("password") String password);

    List<User> findAllCommonUser();

    void addCommonUser(@Param("user") User user);

    User findByEmail(@Param("email") String email);

    User findById(@Param("userId") int userId);

    void updateUser(@Param("user") User user);

    void updateUserStatusToBad(@Param("userId") int userId);

    void updateUserStatusToGood(@Param("userId") int userId);

    List<User> findCommonByUserName(@Param("username") String username);

    void updateUsernameAndPicture(@Param("user") User user);
}
