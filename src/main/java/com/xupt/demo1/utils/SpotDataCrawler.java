package com.xupt.demo1.utils;

import com.xupt.demo1.entity.Spot;
import com.xupt.demo1.entity.SpotDetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author 小关同学
 * @create 2021/11/6
 * 爬取去哪儿旅行的数据
 */
public class SpotDataCrawler {

    /**
     * 爬取对应城市景点简介信息
     * @param city
     * @return
     */
    public static Set<Spot> requestSpotData(String city, int page) {
        String ascii = CodeTransition.stringToASCII(city);
        String target = Request.doGet("http://piao.qunar.com/ticket/list.htm?keyword="+ascii+"&region=&from=mpl_search_suggest&page="+page);
        Document root_document = null;
        assert target != null;
        //是404页面时，返回null
        if (target.contains("<h2>非常抱歉，您访问的页面不存在。</h2>")){
            return null;
        }
        root_document = Jsoup.parse(target);

        //获取需要数据的div
        Element e = root_document.getElementById("search-list");

        //得到网页上的景点列表(包含两个样式集合)
        Elements yy = e.getElementsByClass("sight_item sight_itempos");
        Elements yy2 = e.getElementsByClass("sight_item");
        yy.addAll(yy2);

        //图片
        //#search-list > div:nth-child(1) > div > div.sight_item_show > div > a

        //标题
        //#search-list > div:nth-child(1) > div > div.sight_item_about > h3 > a

        //地点
        //#search-list > div:nth-child(1) > div > div.sight_item_about > div > p > span

        //简介
        //#search-list > div:nth-child(1) > div > div.sight_item_about > div > div.intro.color999

        //热度
        //#search-list > div:nth-child(1) > div > div.sight_item_about > div > div.clrfix > div > span.product_star_level > em > span

        Set<Spot> spotList = new HashSet<>();
        for (int i = 0; i < yy.size(); i++) {

            Spot spot = new Spot();

            //得到每一条景点信息
            Element Info = yy.get(i);
            //分析网页得到景点的标题（使用选择器语法来查找元素）

            //景点名称信息
            Element nameStr = Info.selectFirst(" div > div.sight_item_about > h3 > a");
            String name = nameStr.html();
            spot.setName(name);
            System.out.println("景点名称：" + name);

            //景点图片信息
            Element pictureStr = Info.selectFirst("div > div.sight_item_show > div > a");
            String picture = pictureStr.html();
            int index = picture.indexOf(" alt");
            String url = picture.substring(20,index-1);
            spot.setPicture(url);
            System.out.println("景点图片地址：" + url);

            //价格
            Element priceStr = Info.selectFirst("div > div.sight_item_pop > table > tbody > tr:nth-child(1) > td > span > em");
            if (priceStr!=null){
                String price = priceStr.html();
                if (!price.isEmpty()){
                    spot.setPrice(Double.parseDouble(price));
                    System.out.println("价格：" + price);
                }
            }

            //景点地点信息
            Element addressStr = Info.selectFirst(" div > div.sight_item_about > div > p > span");
            String address = addressStr.html();
            spot.setArea(address);
            System.out.println("景点地点：" + address);

            //景点简介
            Element infoStr = Info.selectFirst(" div > div.sight_item_about > div > div.intro.color999");
            String info = infoStr.html();
            spot.setInfo(info);
            System.out.println("景点简介：" + info);

            //景点在网页中对应的id
            //#search-list > div:nth-child(1) > div > div.sight_item_about > div > p > a
            Element spotIdInWebStr = Info.selectFirst("div > div.sight_item_about > div > p > a");
            int start = spotIdInWebStr.toString().indexOf("data-sightid=\"");
            int end = spotIdInWebStr.toString().indexOf("\">地图");
            String spotWebId = spotIdInWebStr.toString().substring(start+14,end);
            spot.setSpotWebId(spotWebId);
            System.out.println("对应详情页面的id："+spotWebId);
            spotList.add(spot);
        }
        return spotList;
    }

    /**
     * 爬取景点的详细信息
     * @param name 景点名称
     * @param webId 景点对应的webId
     * @return 返回相关信息
     */
    public static Object[] requestSpotDetailData(String name,int id,String webId){
        Object[] result = new Object[2];
        Spot spot = new Spot();
        List<SpotDetail> list = new ArrayList<>();

        String ascii = CodeTransition.stringToASCII(name);
        String url = "http://piao.qunar.com/ticket/detail_" + webId + ".html?st="+ascii+"#from=mpl_search_suggest";
        String target = Request.doGet(url);
        assert target != null;
        //是404页面时，返回null
        if (target.contains("<h2>非常抱歉，您访问的页面不存在。</h2>")){
            System.out.println("非常抱歉，您访问的页面不存在");
            return null;
        }
        Document root_document;
        root_document = Jsoup.parse(target);

        //分析网页得到景点的标题（使用选择器语法来查找元素）

        spot.setId(id);

        //特色看点->推荐理由(保存在Spot里面)
        //#mp-charact > div > div.mp-charact-intro > div.mp-charact-desc > p:nth-child(1)
        //#mp-charact > div > div.mp-charact-intro > div.mp-charact-desc > p:nth-child(2)
        String reason = "";
        Element reasonStr1 = root_document.selectFirst("#mp-charact > div:nth-child(1) > div.mp-charact-intro > div.mp-charact-desc > p");
        if (reasonStr1!=null){
            reason = reasonStr1.html();
        }
        Element reasonStr2 = root_document.selectFirst("#mp-charact > div > div.mp-charact-intro > div.mp-charact-desc > p:nth-child(1)");
        if (reasonStr2!=null){
            reason = reasonStr2.html();
        }
        spot.setInfoDetail(reason);
        System.out.println("推荐理由：" + reason);

        //开放时间
        Element openTimeStr = root_document.selectFirst("#mp-charact > div:nth-child(1) > div.mp-charact-time > div > div.mp-charact-desc > p");
        if (openTimeStr!=null){
            String openTime = openTimeStr.html();
            spot.setOpenTime(openTime);
            System.out.println("开放时间：" + openTime);
        }

        //景点详细信息
        //#mp-charact > div:nth-child(2)
        //#mp-charact > div:nth-child(2)
        Element infoDetailStr = root_document.selectFirst("#mp-charact > div:nth-child(2)");
        //如果没有图片等详细介绍
        if (infoDetailStr==null){
            result[0] = spot;
            result[1] = null;
            return result;
        }
        //得到网页上的景点列表(包含两个样式集合)
        Elements yy = infoDetailStr.getElementsByClass("mp-charact-event");
        System.out.println(yy.size());
        for (int i = 0; i < yy.size(); i++) {

            SpotDetail spotDetail = new SpotDetail();


            //得到每一条景点信息
            Element Info = yy.get(i);

            //#mp-charact > div:nth-child(2) > div:nth-child(3) > div > img
            //#mp-charact > div:nth-child(2) > div:nth-child(3) > div
            Element pictureStr = Info.selectFirst("div > img");
            if (pictureStr!=null){
                int index = pictureStr.toString().indexOf(">");
                String pictureUrl = pictureStr.toString().substring(10,index-1);
                spotDetail.setPicture(pictureUrl);
                System.out.println("景点图片地址：" + pictureUrl);
            }

            //#mp-charact > div:nth-child(2) > div:nth-child(2) > div > div.mp-event-desc > h3
            Element titleStr = Info.selectFirst("div > div.mp-event-desc > h3");
            if (titleStr!=null){
                String title = titleStr.html();
                spotDetail.setTitle(title);
                System.out.println("图片标题："+title);
            }

            Element pictureDetailStr = Info.selectFirst("div > div.mp-event-desc > p");
            if (pictureDetailStr!=null){
                String pictureDetail = pictureDetailStr.html();
                spotDetail.setInfo(pictureDetail);
                System.out.println("图片详情："+pictureDetail);
            }

            list.add(spotDetail);

        }

        result[0] = spot;
        result[1] = list;
        return result;
    }


    public static void main(String[] args) throws InterruptedException {
//        for (int i = 1;i <= 10;i++){
//            //生成1-10的随机数
//            int random = (int)(1+Math.random()*(10-1+1));
//            String str = random+"00";
//            Thread.sleep(Integer.parseInt(str));
//            System.out.println("=======================第"+i+"页=====================");
//            Object result = requestSpotData("西安",i);
//            if (result==null){
//                i--;
//            }
//        }

        Object[] param = SpotDataCrawler.requestSpotDetailData("陕西历史博物馆",1,"383907200");
        System.out.println("===========================分割线==================================");
        if (param!=null){
            System.out.println(((Spot)param[0]).toString());
            for (SpotDetail detail:(List<SpotDetail>)param[1]){
                System.out.println(detail.toString());
            }
        }
    }
}