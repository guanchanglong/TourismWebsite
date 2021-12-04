package com.xupt.demo1.controller;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 小关同学
 * @create 2021/11/17
 * 酒店接口集合
 */
@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    /**
     * 按酒店名字查询
     * @param model
     * @param pageNum
     * @param size
     * @param hotelName
     * @return
     */
    @GetMapping("/searchHotel")
    public String searchHotel(Model model,
                              @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                              @RequestParam(value = "size",defaultValue = "10")int size,
                              @RequestParam(value = "hotelName") String hotelName){
        PageInfo<Hotel> pageInfo = hotelService.findAllByName(hotelName,pageNum,size);
        model.addAttribute("page",pageInfo);
        model.addAttribute("hotelName",hotelName);
        return "user/hotelsToSearch";
    }
}
