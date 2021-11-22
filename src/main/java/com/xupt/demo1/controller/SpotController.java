package com.xupt.demo1.controller;

import com.github.pagehelper.PageInfo;
import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.entity.SpotDetail;
import com.xupt.demo1.entity.User;
import com.xupt.demo1.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/11/10
 * 景点接口集合
 */
@Controller
@RequestMapping("/spot")
public class SpotController {

    @Autowired
    private SpotService spotService;

    /**
     * 景点总览展示
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/showSpot")
    public String showClassList(Model model,
                                HttpSession session,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        PageInfo<Spot> spotPage = spotService.findAll(pageNum,pageSize);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("page", spotPage);
        return "user/spots";
    }

    /**
     * 景点搜索
     * @param model
     * @param spotName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/searchSpot")
    public String searchSpot(Model model,
                             HttpSession session,
                             @RequestParam(value = "spotName") String spotName,
                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        PageInfo<Spot> spotPage = spotService.findAllByName(spotName, pageNum, pageSize);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("page", spotPage);
        model.addAttribute("name",spotName);
        return "user/spotsToSearch";
    }

    /**
     * 景点首页数据展示，就4条记录
     * @param model
     * @return
     */
    @GetMapping("/indexShowFourData")
    public String indexShowFourData(Model model,HttpSession session){
        List<Spot> list = spotService.findFourData();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("spotShow",list);
        return "user/index";
    }

    //景点详情页面需要的数据
    // 1、景点名称
    // 2、景点简介
    // 3、景点位置
    // 4、门票价格
    // 5、特色看点（SpotDetail表里面的内容，全都要）

    /**
     * 景点详情页面的数据展示
     * @param model
     * @param spotId
     * @return
     */
    @GetMapping("/showSpotDetail")
    public String showSpotDetail(Model model,
                                 HttpSession session,
                                 @RequestParam(value = "spotId")int spotId){
        Spot spot = spotService.findSpotById(spotId);
        List<SpotDetail> spotDetailList = spotService.findSpotDetailBySpotId(spotId);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("spot",spot);
        model.addAttribute("spotDetailList",spotDetailList);
        return "user/spotDetail";
    }

}
