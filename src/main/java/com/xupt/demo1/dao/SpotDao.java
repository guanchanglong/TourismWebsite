package com.xupt.demo1.dao;

import com.xupt.demo1.entity.Spot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/7
 */
@Mapper
public interface SpotDao {

    void insertSpotData(@Param("spot")Spot spot);

    List<Spot> findAllByName(@Param("name")String name);

    List<Spot> findAll();

    List<Spot> findFourData();

    Spot findSpotById(@Param("spotId") int spotId);
}
