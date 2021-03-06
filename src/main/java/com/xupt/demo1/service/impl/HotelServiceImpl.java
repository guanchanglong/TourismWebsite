package com.xupt.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.demo1.dao.HotelDao;
import com.xupt.demo1.dao.RoomDao;
import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelDao hotelDao;

    @Autowired
    private RoomDao roomDao;

    /**
     * 酒店页面的分页
     * @param pageNum 页码
     * @param size 每页的数目
     * @return
     */
    @Override
    public PageInfo<Hotel> findAll(int pageNum,int size){
        PageHelper.startPage(pageNum,size);
        List<Hotel> list = hotelDao.findAll();
        for (Hotel hotel:list){
            //设置酒店的最低价格用于展示
            Object price = roomDao.findMinPriceByHotelId(hotel.getId());
            if (price!=null){
                hotel.setMinPrice(price.toString());
            }else{
                hotel.setMinPrice("暂无");
            }
            if (hotel.getInfo().length()>50){
                String info = hotel.getInfo().substring(0,50);
                info+="...";
                hotel.setInfo(info);
            }
        }
        return new PageInfo<>(list);
    }
    /**
     * 按名字查找酒店并分页
     * @param hotelName
     * @param pageNum
     * @param size
     * @return
     */
    @Override
    public PageInfo<Hotel> findAllByName(String hotelName,int pageNum,int size){
        PageHelper.startPage(pageNum,size);
        List<Hotel> list = hotelDao.findAllByName(hotelName);
        return new PageInfo<>(list);
    }

    @Override
    public void addHotel(Hotel hotel){
        hotelDao.addHotel(hotel);
    }

    @Override
    public Hotel findHotelById(int hotelId){
        return hotelDao.findAllById(hotelId);
    }

    @Override
    public void updateHotel(Hotel hotel){
        hotelDao.updateHotel(hotel);
    }

    @Override
    public void deleteHotel(int hotelId){
        //删除酒店下对应的房间
        roomDao.deleteByHotelId(hotelId);
        //删除酒店
        hotelDao.deleteHotel(hotelId);
    }

    @Override
    public void updateHotelStatusToGood(int hotelId){
        hotelDao.updateHotelStatusToGood(hotelId);
    }

    @Override
    public void updateHotelStatusToBad(int hotelId){
        hotelDao.updateHotelStatusToBad(hotelId);
    }

    @Override
    public PageInfo<Hotel> findByName(int pageNum,
                                      int size,
                                      String hotelName){
        PageHelper.startPage(pageNum,size);
        List<Hotel> list = hotelDao.findByName("%"+hotelName+"%");
        return new PageInfo<>(list);
    }

}
