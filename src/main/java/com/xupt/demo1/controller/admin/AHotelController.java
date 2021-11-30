package com.xupt.demo1.controller.admin;

import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Controller
@RequestMapping("/admin/hotel")
public class AHotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/addHotel")
    public String addHotel(@RequestParam(value = "hotelName") String hotelName,
                           @RequestParam(value = "hotelPicture") String hotelPicture,
                           @RequestParam(value = "hotelAddress") String hotelAddress,
                           @RequestParam(value = "hotelPhone") String hotelPhone,
                           @RequestParam(value = "hotelType") String hotelType,
                           @RequestParam(value = "hotelInfo") String hotelInfo,
                           RedirectAttributes attributes){
        Hotel hotel = new Hotel();
        hotel.setName(hotelName);
        hotel.setPicture(hotelPicture);
        hotel.setAddress(hotelAddress);
        hotel.setPhone(hotelPhone);
        hotel.setType(hotelType);
        hotel.setInfo(hotelInfo);
        hotelService.addHotel(hotel);
        attributes.addFlashAttribute("message","添加成功");
        return "redirect:/admin/page/toHotelAddPage";
    }
}
