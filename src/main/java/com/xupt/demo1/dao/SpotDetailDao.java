package com.xupt.demo1.dao;

import com.xupt.demo1.entity.SpotDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Mapper
public interface SpotDetailDao {
    SpotDetail findSpotDetailById(@Param("spotDetailId") int spotDetailId);
}
