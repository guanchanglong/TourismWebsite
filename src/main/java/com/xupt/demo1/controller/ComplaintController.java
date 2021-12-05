package com.xupt.demo1.controller;

import com.xupt.demo1.entity.Complaint;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author 小关同学
 * @create 2021/12/5
 */
@Controller
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/addComplaint")
    public String addComplaint(HttpSession session,
                               RedirectAttributes attributes,
                               @RequestParam(value = "content") String content){
        User user = (User) session.getAttribute("user");
        Date date = new Date();
        Complaint complaint = new Complaint();
        complaint.setEmail(user.getEmail());
        complaint.setTime(date);
        complaint.setContent(content);
        complaintService.insertComplaint(complaint);
        attributes.addFlashAttribute("message","感谢您的反馈");
        return "redirect:/page/toComplaintPage";
    }
}
