package com.xupt.demo1.controller;

import com.xupt.demo1.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @author 小关同学
 * @create 2021/11/5
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/toTicketPage")
    public String toHotelPage(Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("trainInformation", new ArrayList<>());
        return "user/ticket";
    }

    @RequestMapping("/toOrderPage")
    public String toOrderPage(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        model.addAttribute("user",user);
        return "user/order";
    }

    @RequestMapping("/toSpotsPage")
    public String toSpotsPage(){
        return "redirect:../spot/showSpot";
    }

    @RequestMapping("/toHotelPage")
    public String toSpotsRecommend(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        model.addAttribute("user",user);
        return "user/hotels";
    }

    @RequestMapping("/index")
    public String index(){
        return "redirect:../spot/indexShowFourData";
    }

    @RequestMapping("/toSpotDetailPage")
    public String toSpotDetailPage(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        model.addAttribute("user",user);
        return "user/spotsDetails";
    }

}
