package com.xupt.demo1.service;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Hotel;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
public interface HotelService {
    PageInfo<Hotel> findAll(int pageNum, int size);

    PageInfo<Hotel> findAllByName(String hotelName,int pageNum,int size);

    void addHotel(Hotel hotel);

    Hotel findHotelById(int hotelId);

    void updateHotel(Hotel hotel);

    void deleteHotel(int hotelId);

    void updateHotelStatusToGood(int hotelId);

    void updateHotelStatusToBad(int hotelId);

    PageInfo<Hotel> findByName(int pageNum,int size,String hotelName);
}
