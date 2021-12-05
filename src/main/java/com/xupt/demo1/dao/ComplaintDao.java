package com.xupt.demo1.dao;

import com.xupt.demo1.entity.Complaint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/4
 */
@Mapper
public interface ComplaintDao {

    List<Complaint> findAll();

    void updateStatus(@Param("complaintId") int complaintId);

    void deleteComplaint(@Param("complaintId") int complaintId);

    List<Complaint> findAllByStatus();

    void insertComplaint(@Param("complaint") Complaint complaint);
}
