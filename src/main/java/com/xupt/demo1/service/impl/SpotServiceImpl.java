package com.xupt.demo1.service.impl;

import com.xupt.demo1.dao.SpotAndSpotDetailDao;
import com.xupt.demo1.dao.SpotDao;
import com.xupt.demo1.dao.SpotDetailDao;
import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.entity.SpotDetail;
import com.xupt.demo1.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/7
 */
@Service
public class SpotServiceImpl implements SpotService {
    @Autowired
    private SpotDao spotDao;

    @Autowired
    private SpotDetailDao spotDetailDao;

    @Autowired
    private SpotAndSpotDetailDao spotAndSpotDetailDao;

    @Override
    public void insertSpotData(Spot spot) {
        spotDao.insertSpotData(spot);
    }

    @Override
    public PageInfo<Spot> findAllByName(String name,
                                    int pageNum,
                                    int size){
        PageHelper.startPage(pageNum,size);
        List<Spot> list = spotDao.findAllByName("%"+name+"%");
        for (Spot spot:list){
            //如果没有简介，则设置简介为暂无
            if (spot.getInfo()==null||spot.getInfo().isEmpty()){
                spot.setInfo("暂无");
            }
            //如果简介长度太长，则去除过长部分，改为省略号
            if (spot.getInfo().length()>30){
                String info = spot.getInfo();
                info = info.substring(0,30);
                info+="...";
                spot.setInfo(info);
            }
            //如果景点名字过长，则去除过长部分改为省略号
            if (spot.getName().length()>10){
                String target = spot.getName();
                target = target.substring(0,10);
                target+="...";
                spot.setName(target);
            }
        }
        PageInfo<Spot> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 返回前size页的景点列表
     * @param size 返回页码数目
     * @return 返回景点列表
     */
    @Override
    public PageInfo<Spot> findAll(int pageNum, int size){
        PageHelper.startPage(pageNum,size);
        List<Spot> list = spotDao.findAll();
        for (Spot spot:list){
            //如果没有简介，则设置简介为暂无
            if (spot.getInfo()==null||spot.getInfo().isEmpty()){
                spot.setInfo("暂无");
            }
            //如果简介长度太长，则去除过长部分，改为省略号
            if (spot.getInfo().length()>30){
                String info = spot.getInfo();
                info = info.substring(0,30);
                info+="...";
                spot.setInfo(info);
            }
            //如果景点名字过长，则去除过长部分改为省略号
            if (spot.getName().length()>10){
                String name = spot.getName();
                name = name.substring(0,10);
                name+="...";
                spot.setName(name);
            }
        }
        PageInfo<Spot> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<Spot> findFourData(){
        return spotDao.findFourData();
    }

    @Override
    public Spot findSpotById(int spotId){
        return spotDao.findSpotById(spotId);
    }

    @Override
    public List<SpotDetail> findSpotDetailBySpotId(int spotId){
        List<Integer> list = spotAndSpotDetailDao.findSpotDetailId(spotId);
        List<SpotDetail> result = new ArrayList<>();
        for (int spotDetailId:list){
            SpotDetail spotDetail = spotDetailDao.findSpotDetailById(spotDetailId);
            result.add(spotDetail);
        }
        return result;
    }

}