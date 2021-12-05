package com.xupt.demo1.service;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Complaint;

/**
 * @author 小关同学
 * @create 2021/12/4
 */
public interface ComplaintService {
    PageInfo<Complaint> findAll(int pageNum, int size);

    void updateStatus(int complaintId);

    void deleteComplaint(int complaintId);

    int findAllByStatusCount();

    void insertComplaint(Complaint complaint);
}
