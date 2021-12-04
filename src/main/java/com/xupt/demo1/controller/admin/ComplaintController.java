package com.xupt.demo1.controller.admin;

import com.xupt.demo1.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 小关同学
 * @create 2021/12/4
 */
@Controller
@RequestMapping("/admin/complaint")
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;

    @RequestMapping("/updateComplaintStatus")
    public String updateComplaintStatus(@RequestParam("complaintId") int complaintId){
        complaintService.updateStatus(complaintId);
        return "redirect:/admin/page/toComplaintPage";
    }

    @RequestMapping("/deleteComplaint")
    public String deleteComplaint(@RequestParam("complaintId") int complaintId){
        complaintService.deleteComplaint(complaintId);
        return "redirect:/admin/page/toComplaintPage";
    }
}
