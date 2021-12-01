package com.xupt.demo1.controller.admin;

import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/updateHotel/{hotelId}")
    public String updateHotel(@RequestParam(value = "hotelName") String hotelName,
                              @RequestParam(value = "hotelPicture") String hotelPicture,
                              @RequestParam(value = "hotelAddress") String hotelAddress,
                              @RequestParam(value = "hotelPhone") String hotelPhone,
                              @RequestParam(value = "hotelType") String hotelType,
                              @RequestParam(value = "hotelInfo") String hotelInfo,
                              @PathVariable(value = "hotelId") int hotelId,
                              RedirectAttributes attributes){
        Hotel hotel = new Hotel();
        System.out.println(hotelName);

        hotel.setId(hotelId);
        if (hotelName.isEmpty()){
            hotel.setName(null);
        }else{
            hotel.setName(hotelName);
        }
        if (hotelPicture.isEmpty()){
            hotel.setPicture(null);
        }else{
            hotel.setPicture(hotelPicture);
        }
        if (hotelAddress.isEmpty()){
            hotel.setAddress(null);
        }else{
            hotel.setAddress(hotelAddress);
        }
        if (hotelPhone.isEmpty()){
            hotel.setPhone(null);
        }else{
            hotel.setPhone(hotelPhone);
        }
        if (hotelType.isEmpty()){
            hotel.setType(null);
        }else{
            hotel.setType(hotelType);
        }
        if (hotelInfo.isEmpty()){
            hotel.setInfo(null);
        }else{
            hotel.setInfo(hotelInfo);
        }
        hotelService.updateHotel(hotel);
        attributes.addFlashAttribute("message","更新成功");
        return "redirect:/admin/page/toHotelModifyPage?hotelId=" + hotelId;
    }

    @GetMapping("/deleteHotel")
    public String deleteHotel(@RequestParam("hotelId") int hotelId){
        hotelService.deleteHotel(hotelId);
        return "redirect:/admin/page/toHotelPage";
    }
}