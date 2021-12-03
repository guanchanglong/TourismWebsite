package com.xupt.demo1.controller.admin;

import com.xupt.demo1.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/17
 */
@Controller
@RequestMapping("/admin/spot")
public class ASpotController {

    @Autowired
    private SpotService spotService;

    @RequestMapping("/updateSpotData")
    public String updateSpotData(RedirectAttributes attributes){
        //爬取更新10页的景点简要信息
        spotService.getSpotDataAndSave(10);
        attributes.addFlashAttribute("message","景点信息更新成功");
        return "redirect:/admin/page/toSpotsPage";
    }

    @RequestMapping("/updateSpotDetailData")
    public String updateSpotDetailData(RedirectAttributes attributes){
        List<Object> list = spotService.getAllDetailData();
        spotService.findAllSpotNameAndSpotWebId(list);
        attributes.addFlashAttribute("message","景点详细信息更新成功");
        return "redirect:/admin/page/toSpotsPage";
    }


}
