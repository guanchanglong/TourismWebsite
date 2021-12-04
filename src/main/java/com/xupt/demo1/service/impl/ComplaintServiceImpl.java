package com.xupt.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xupt.demo1.dao.ComplaintDao;
import com.xupt.demo1.entity.Complaint;
import com.xupt.demo1.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/4
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintDao complaintDao;

    @Override
    public PageInfo<Complaint> findAll(int pageNum,int size){
        PageHelper.startPage(pageNum,size);
        List<Complaint> list = complaintDao.findAll();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        for (Complaint complaint:list){
            complaint.setTimeToShow(format.format(complaint.getTime()));
        }
        return new PageInfo<>(list);
    }

    @Override
    public void updateStatus(int complaintId){
        complaintDao.updateStatus(complaintId);
    }

    @Override
    public void deleteComplaint(int complaintId){
        complaintDao.deleteComplaint(complaintId);
    }

    @Override
    public int findAllByStatusCount(){
        return complaintDao.findAllByStatus().size();
    }
}
