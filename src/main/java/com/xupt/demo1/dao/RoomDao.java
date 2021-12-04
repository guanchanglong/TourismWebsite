package com.xupt.demo1.dao;

import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Mapper
public interface RoomDao {

    List<Room> findByHotelId(@Param("hotelId") int hotelId);

    void addRoom(@Param("room") Room room);

    Room findById(@Param("roomId") int roomId);

    void updateRoom(@Param("room") Room room);

    void deleteRoom(@Param("roomId") int roomId);

    void deleteByHotelId(@Param("hotelId") int hotelId);

    Object findMinPriceByHotelId(@Param("hotelId") int hotelId);

}
