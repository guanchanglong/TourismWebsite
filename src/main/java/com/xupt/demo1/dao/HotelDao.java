package com.xupt.demo1.dao;

import com.xupt.demo1.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Mapper
public interface HotelDao {

    List<Hotel> findAll();

    List<Hotel> findAllByName(@Param("hotelName") String hotelName);

    Hotel findAllById(@Param("hotelId")int hotelId);

    void addHotel(@Param("hotel") Hotel hotel);

    void updateHotel(@Param("hotel") Hotel hotel);

    void deleteHotel(@Param("hotelId") int hotelId);

}
