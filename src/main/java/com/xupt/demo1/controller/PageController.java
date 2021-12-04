package com.xupt.demo1.controller;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @author 小关同学
 * @create 2021/11/5
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    private HotelService hotelService;

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
    public String toSpotsRecommend(Model model,
                                   HttpSession session,
                                   @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                   @RequestParam(value = "size",defaultValue = "10") int size){
        PageInfo<Hotel> pageInfo = hotelService.findAll(pageNum, size);
        model.addAttribute("page", pageInfo);
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
