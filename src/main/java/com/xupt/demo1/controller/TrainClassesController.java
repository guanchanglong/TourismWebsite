package com.xupt.demo1.controller;

import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.entity.TrainClasses;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.SpotService;
import com.xupt.demo1.utils.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/17
 * 火车票查询接口集合
 */
@Controller
@RequestMapping("/trainClasses")
public class TrainClassesController {

    @Autowired
    private TicketRequest ticketRequest;

    /**
     * 火车票查询，只能查，不能再查看详情
     * @param model
     * @param startDate
     * @param startCity
     * @param endCity
     * @return
     */
    @GetMapping("/searchClasses")
    public String searchClasses(Model model,
                                HttpSession session,
                                @RequestParam String startDate,
                                @RequestParam String startCity,
                                @RequestParam String endCity){
        List<TrainClasses> list = ticketRequest.getTicketInfo(startDate,startCity,endCity);
        if (list!=null||!list.isEmpty()){
            model.addAttribute("information",startCity+" --> "+ endCity + "(" + startDate + ") 共计" + list.size() + "次车次");
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("trainInformation",list);
        return "user/ticket";
    }
}
