package com.xupt.demo1.service;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Room;

/**
 * @author 小关同学
 * @create 2021/11/30
 */
public interface RoomService {

    PageInfo<Room> findByHotelId(int pageNum, int size,int hotelId);

    void addRoom(Room room);

    Room findById(int roomId);

    void updateRoom(Room room);

    void deleteRoom(int roomId);
}
