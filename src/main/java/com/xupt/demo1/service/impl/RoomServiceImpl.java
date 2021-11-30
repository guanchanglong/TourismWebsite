package com.xupt.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.demo1.dao.RoomDao;
import com.xupt.demo1.entity.Room;
import com.xupt.demo1.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/30
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Override
    public PageInfo<Room> findByHotelId(int pageNum,int size,int hotelId){
        PageHelper.startPage(pageNum,size);
        List<Room> list = roomDao.findByHotelId(hotelId);
        return new PageInfo<>(list);
    }

    @Override
    public void addRoom(Room room){
        roomDao.addRoom(room);
    }

    @Override
    public Room findById(int roomId){
        return roomDao.findById(roomId);
    }

    @Override
    public void updateRoom(Room room){
        roomDao.updateRoom(room);
    }

    @Override
    public void deleteRoom(int roomId){
        roomDao.deleteRoom(roomId);
    }

}
