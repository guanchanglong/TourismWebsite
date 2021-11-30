package com.xupt.demo1.controller.admin;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Hotel;
import com.xupt.demo1.entity.Room;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.HotelService;
import com.xupt.demo1.service.RoomService;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/29
 */
@Controller
@RequestMapping("/admin/page")
public class APageController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
        return "admin/login";
    }

    @RequestMapping("/toIndexPage")
    public String toIndexPage(){
        return "admin/index";
    }

    @RequestMapping("/toHotelPage")
    public String toHotelPage(Model model,
                              @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "size",defaultValue = "10") int size,
                              HttpSession session){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<Hotel> list = hotelService.findAll(pageNum,size);

        model.addAttribute("adminUser",user);
        model.addAttribute("page",list);
        return "admin/hotel";
    }

    @RequestMapping("/toOrderListPage")
    public String toOrderListPage(){
        return "admin/order-list";
    }

    @RequestMapping("/toHotelDetailPage")
    public String toHotelDetailPage(){
        return "admin/hotel-detail";
    }

    @RequestMapping("/toHotelModifyPage")
    public String toHotelModifyPage(){
        return "admin/hotel-modify";
    }

    @RequestMapping("/toHotelAddPage")
    public String toHotelAddPage(){
        return "admin/hotel-add";
    }

    @RequestMapping("/toSpotsPage")
    public String toSpotsPage(){
        return "admin/spots";
    }

    @RequestMapping("/toSpotsDetailPage")
    public String toSpotsDetailPage(){
        return "admin/spots-detail";
    }

    @RequestMapping("/toSpotsAddPage")
    public String toSpotsAddPage(){
        return "admin/spots-add";
    }

    @RequestMapping("/toSpotsModifyPage")
    public String toSpotsModifyPage(){
        return "admin/spots-modify";
    }

    @RequestMapping("/toUsersPage")
    public String toUsersPage(){
        return "admin/users";
    }

    @RequestMapping("/toProfilePage")
    public String toProfilePage(){
        return "admin/profile";
    }

    @RequestMapping("/toSettingPage")
    public String toSettingPage(){
        return "admin/setting";
    }

    @RequestMapping("/toRoomPage")
    public String toRoomPage(Model model,
                             @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                             @RequestParam(value = "size",defaultValue = "10") int size,
                             HttpSession session,
                             @RequestParam(value = "hotelId") int hotelId){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<Room> list = roomService.findByHotelId(pageNum,size,hotelId);
        Hotel hotel = hotelService.findHotelById(hotelId);

        model.addAttribute("hotel",hotel);
        model.addAttribute("user",user);
        model.addAttribute("page",list);
        return "admin/room";
    }

    @RequestMapping("/toRoomAddPage")
    public String toRoomAddPage(Model model,
                                HttpSession session,
                                @RequestParam(value = "hotelId") int hotelId){
        User user  = (User)session.getAttribute("adminUser");
        model.addAttribute("adminUser",user);

        model.addAttribute("hotelId", hotelId);
        return "admin/room-add";
    }

    @RequestMapping("/toRoomModifyPage")
    public String toRoomModifyPage(Model model,
                                   HttpSession session,
                                   @RequestParam("roomId") int roomId){
        Room room = roomService.findById(roomId);
        User user  = (User)session.getAttribute("adminUser");

        model.addAttribute("room",room);
        model.addAttribute("adminUser",user);
        return "admin/room-modify";
    }

}
