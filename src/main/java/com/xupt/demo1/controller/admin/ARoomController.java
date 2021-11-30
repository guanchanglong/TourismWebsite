package com.xupt.demo1.controller.admin;

import com.xupt.demo1.entity.Room;
import com.xupt.demo1.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 小关同学
 * @create 2021/11/30
 */
@Controller
@RequestMapping("/admin/room")
public class ARoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/addRoom/{hotelId}")
    public String addRoom(@RequestParam(value = "roomTitle") String roomTitle,
                          @RequestParam(value = "roomPicture") String roomPicture,
                          @RequestParam(value = "roomType") String roomType,
                          @RequestParam(value = "roomPrice") int roomPrice,
                          @RequestParam(value = "roomInfo") String roomInfo,
                          @PathVariable(value = "hotelId") int hotelId,
                          RedirectAttributes attributes){
        Room room = new Room();
        room.setTitle(roomTitle);
        room.setPicture(roomPicture);
        room.setType(roomType);
        room.setPrice(roomPrice);
        room.setInfo(roomInfo);
        room.setHotelId(hotelId);
        roomService.addRoom(room);
        attributes.addFlashAttribute("message","添加成功");
        return "redirect:/admin/page/toRoomAddPage?hotelId=" + hotelId;
    }

    @PostMapping("/updateRoom/{roomId}")
    public String updateRoom(@RequestParam(value = "roomTitle") String roomTitle,
                             @RequestParam(value = "roomPicture") String roomPicture,
                             @RequestParam(value = "roomType") String roomType,
                             @RequestParam(value = "roomPrice") int roomPrice,
                             @RequestParam(value = "roomInfo") String roomInfo,
                             @PathVariable(value = "roomId") int roomId,
                             RedirectAttributes attributes){
        Room room = new Room();
        room.setId(roomId);
        room.setTitle(roomTitle);
        room.setPicture(roomPicture);
        room.setType(roomType);
        room.setPrice(roomPrice);
        room.setInfo(roomInfo);
        roomService.updateRoom(room);
        attributes.addFlashAttribute("message","修改成功");
        return "redirect:/admin/page/toRoomModifyPage?roomId=" + roomId;

    }

    @GetMapping("/deleteRoom/{hotelId}")
    public String deleteRoom(@RequestParam("roomId") int roomId,
                             @PathVariable("hotelId") int hotelId){
        roomService.deleteRoom(roomId);
        return "redirect:/admin/page/toRoomPage?hotelId=" + hotelId;
    }

}
