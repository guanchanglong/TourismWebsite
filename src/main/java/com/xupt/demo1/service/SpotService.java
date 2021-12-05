package com.xupt.demo1.service;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.entity.SpotDetail;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/7
 */
public interface SpotService {
    void insertSpotData(Spot spot);

    PageInfo<Spot> findAllByName(String name, int pageNum, int size);

    PageInfo<Spot> findAll(int pageNum, int size);

    List<Spot> findFourData();

    Spot findSpotById(int spotId);

    List<SpotDetail> findSpotDetailBySpotId(int spotId);

    void getSpotDataAndSave(int pageNum);

    void findAllSpotNameAndSpotWebId();
}
