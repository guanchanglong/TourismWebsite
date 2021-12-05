package com.xupt.demo1.service.impl;

import com.xupt.demo1.controller.admin.APageController;
import com.xupt.demo1.dao.SpotAndSpotDetailDao;
import com.xupt.demo1.dao.SpotDao;
import com.xupt.demo1.dao.SpotDetailDao;
import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.entity.SpotDetail;
import com.xupt.demo1.service.SpotService;
import com.xupt.demo1.utils.SpotDataCrawler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

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
//            if (spotDetail.getTitle()==null){
//                spotDetail.setTitle("");
//            }
//            if (spotDetail.getInfo()==null){
//
//            }
            result.add(spotDetail);
        }
        return result;
    }


    /**
     * 爬取页数的景点简介数据并保存
     * @param pageNum 爬取 1~pageNum 页的数据
     */
    @Async
    public void getSpotDataAndSave(int pageNum){

        //现在表中的数据数
        int spotCount = spotDao.spotCount();

        int index = 1;

        //爬取 1~pageNum 页的数据
        for(int i = 1;i <= pageNum;i++){
            //生成1-10的随机数
            int random = (int)(1+Math.random()*(10-1+1));
            String str = random+"00";
            try {
                Thread.sleep(Integer.parseInt(str));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //爬取景点数据
            Set<Spot> list = SpotDataCrawler.requestSpotData("西安",i);

            //为空的话就继续爬，直到获取到数据为止
            if (list==null){
                i--;
            }else{
                //写入景点数据
                //这里不采取主键自增的策略，人为插入主键
                for (Spot spot:list){
                    if (index <= spotCount){
                        //如果未超过现有数据的主键值，则直接更新数据
                        spot.setId(index);
                        spotDao.updateSpotData(spot);
                    }else{
                        //如果超过了现有数据的主键值，则插入新数据
                        spot.setId(index);
                        spotDao.insertSpotData(spot);
                    }
                    index++;
                }
            }
        }
    }


    /**
     * 保存和关联所有景点详细信息
     */
    @Async
    public void findAllSpotNameAndSpotWebId(){

        //获取景点简介数据
        List<Spot> listSpot = spotDao.findAllReturnIdAndNameAndSpotWebId();

        List<Object> result = new ArrayList<>();

        //开始遍历爬取
        for (int i = 0;i < listSpot.size();i++){
            Spot spot = listSpot.get(i);
            Object[] params = SpotDataCrawler.requestSpotDetailData(spot.getName(),spot.getId(),spot.getSpotWebId());

            //生成1-10的随机数
            int random = (int)(1+Math.random()*(10-1+1));
            random *= 100;
            try {
                //停顿一段时间，免得被反爬机制检测出来
                Thread.sleep(random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //爬取信息是否成功
            if (params==null){
                i--;
            }else{
                //成功就添加到集合中去
                result.add(params);
            }
        }



        //获取景点详情界面在数据库的长度
        int totalSpotDetailSize = spotAndSpotDetailDao.countNotNullNum();

        int index = 1;

        //获取所有的景点详细信息
        for (int i = 0;i < result.size();i++){
            Object[]data = (Object[]) result.get(i);

            Spot spotToUpdate = (Spot) data[0];
            if (spotToUpdate!=null){
                //更新Spot的详细信息
                spotDao.updateSpot(spotToUpdate,spotToUpdate.getId());
            }

            //保存每个景点对应的详细信息
            List<SpotDetail> spotDetails = (List<SpotDetail>) data[1];
            if (spotDetails!=null){
                for (SpotDetail spotDetail:spotDetails){
                    spotDetail.setId(index);
                    //如果更新的数据量不超过原来数据库有的数据，则做更新操作
                    //如果超过了，则做插入操作
                    if (index <= totalSpotDetailSize){
                        //更新景点详细信息
                        spotDetailDao.updateSpotDetail(spotDetail);
                        //更新每个景点详细信息和景点简介信息的关系表
                        spotAndSpotDetailDao.updateSpotAndSpotDetail(index,spotToUpdate.getId(),index);
                    }else{
                        //保存景点详细信息
                        spotDetailDao.insertSpotDetailData(spotDetail);
                        //保存每个景点详细信息和景点简介信息的关系表
                        spotAndSpotDetailDao.saveRelation(index,spotToUpdate.getId(),index);
                    }
                    index++;
                }
            }
        }
    }

}