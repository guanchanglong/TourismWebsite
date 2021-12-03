package com.xupt.demo1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Mapper
public interface SpotAndSpotDetailDao {
    List<Integer> findSpotDetailId(@Param("spotId") int spotId);

    void saveRelation(@Param("id")int id,@Param("spotId") int spotId, @Param("spotDetailId") int spotDetailId);

    int countNotNullNum();

    void updateSpotAndSpotDetail(@Param("id")int id,@Param("spotId") int spotId, @Param("spotDetailId") int spotDetailId);
}
