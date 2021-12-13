package com.xupt.demo1.controller.admin;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.*;
import com.xupt.demo1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private ComplaintService complaintService;

    @RequestMapping("/toLoginPage")
    public String toLoginPage(){
        return "admin/login";
    }

    @RequestMapping("/toIndexPage")
    public String toIndexPage(Model model,
                              HttpSession session){
        User user  = (User)session.getAttribute("adminUser");
        model.addAttribute("adminUser",user);

        List<Double> chartData = orderService.returnChartData();
        List<Integer> result = new ArrayList<>(chartData.size());
        for(Double num:chartData){
            result.add(num.intValue());
        }
        model.addAttribute("chatData",result);

        Double[] nums = orderService.statisticsIncome();
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("thisMonthIncome",nums[0].intValue());
        model.addAttribute("todayIncome",nums[1].intValue());
        model.addAttribute("totalIncome",nums[2].intValue());
        model.addAttribute("totalNum", nums[3].intValue());
        return "admin/index";
    }

    @RequestMapping("/toHotelPage")
    public String toHotelPage(Model model,
                              @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "size",defaultValue = "7") int size,
                              HttpSession session){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<Hotel> list = hotelService.findAll(pageNum,size);

        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("adminUser",user);
        model.addAttribute("page",list);
        return "admin/hotel";
    }

    @RequestMapping("/toHotelDetailPage")
    public String toHotelDetailPage(Model model,
                                HttpSession session,
                                @RequestParam(value = "hotelId") int hotelId){
        Hotel hotel = hotelService.findHotelById(hotelId);
        User user  = (User)session.getAttribute("adminUser");

        model.addAttribute("adminUser",user);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("hotel",hotel);
        return "admin/hotel-detail";
    }

    @RequestMapping("/toHotelModifyPage")
    public String toHotelModifyPage(Model model,
                                    HttpSession session,
                                    @RequestParam(value = "hotelId") int hotelId){
        Hotel hotel = hotelService.findHotelById(hotelId);
        User user  = (User)session.getAttribute("adminUser");

        model.addAttribute("hotel",hotel);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("adminUser",user);
        return "admin/hotel-modify";
    }

    @RequestMapping("/toHotelAddPage")
    public String toHotelAddPage(Model model,
                                 HttpSession session){
        User user  = (User)session.getAttribute("adminUser");
        model.addAttribute("adminUser",user);
        return "admin/hotel-add";
    }

    @RequestMapping("/toSpotsPage")
    public String toSpotsPage(Model model,
                              HttpSession session,
                              @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "size",defaultValue = "7") int size){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<Spot> page = spotService.findAll(pageNum, size);

        model.addAttribute("adminUser",user);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("page",page);
        return "admin/spots";
    }

    @RequestMapping("/toUsersPage")
    public String toUsersPage(Model model,
                              HttpSession session,
                              @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "size",defaultValue = "7") int size){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<User> page = userService.findAllCommonUser(pageNum,size);

        model.addAttribute("page",page);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("adminUser",user);
        return "admin/users";
    }

    @RequestMapping("/toUserAddPage")
    public String toUserAddPage(Model model,
                                HttpSession session){
        User user  = (User)session.getAttribute("adminUser");
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("adminUser",user);

        return "admin/user-add";
    }

    @RequestMapping("/toUserModifyPage")
    public String toUserModifyPage(Model model,
                                   HttpSession session,
                                   @RequestParam(value = "userId") int userId){
        User user  = (User)session.getAttribute("adminUser");
        User commonUser = userService.findById(userId);

        model.addAttribute("adminUser", user);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("commonUser", commonUser);
        return "admin/user-modify";
    }

    @RequestMapping("/toUserOrderPage")
    public String toUserOrderPage(Model model,
                                  HttpSession session,
                                  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value = "size",defaultValue = "7") int size,
                                  @RequestParam(value = "userId") int userId){
        PageInfo<Order> pageInfo = orderService.findByUserId(userId,pageNum,size);
        User user  = (User)session.getAttribute("adminUser");

        model.addAttribute("page",pageInfo);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("adminUser", user);
        model.addAttribute("userId",userId);
        return "admin/user-order";

    }

    @RequestMapping("/toProfilePage")
    public String toProfilePage(Model model,
                                HttpSession session){
        User user  = (User)session.getAttribute("adminUser");
        model.addAttribute("adminUser", user);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        return "admin/profile";
    }

    @RequestMapping("/toRoomPage")
    public String toRoomPage(Model model,
                             @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                             @RequestParam(value = "size",defaultValue = "7") int size,
                             HttpSession session,
                             @RequestParam(value = "hotelId") int hotelId){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<Room> list = roomService.findByHotelId(pageNum,size,hotelId);
        Hotel hotel = hotelService.findHotelById(hotelId);

        model.addAttribute("hotel",hotel);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("adminUser",user);
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
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
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
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        return "admin/room-modify";
    }

    @GetMapping("/toSearchHotelPage")
    public String toSearchHotelPage(Model model,
                                    HttpSession session,
                                    @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                    @RequestParam(value = "size",defaultValue = "7") int size,
                                    @RequestParam("hotelName") String hotelName){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<Hotel> page = hotelService.findByName(pageNum, size, hotelName);

        model.addAttribute("hotelName", hotelName);
        model.addAttribute("adminUser",user);
        model.addAttribute("page",page);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        return "admin/hotel-search";
    }

    @GetMapping("/toSearchUserPage")
    public String toSearchUserPage(Model model,
                                   HttpSession session,
                                   @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                   @RequestParam(value = "size",defaultValue = "7") int size,
                                   @RequestParam(value = "username") String username){
        User user  = (User)session.getAttribute("adminUser");
        PageInfo<User> page = userService.findCommonByUserName(pageNum, size, username);

        model.addAttribute("adminUser",user);
        model.addAttribute("username",username);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        model.addAttribute("page",page);
        return "admin/user-search";
    }

    @RequestMapping("/toComplaintPage")
    public String toComplaintPage(Model model,
                                  HttpSession session,
                                  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value = "size",defaultValue = "7") int size){
        User user  = (User)session.getAttribute("adminUser");
        model.addAttribute("adminUser",user);

        PageInfo<Complaint> page = complaintService.findAll(pageNum, size);
        model.addAttribute("page",page);
        model.addAttribute("complaintCount",complaintService.findAllByStatusCount());
        return "admin/complaint";
    }

}
