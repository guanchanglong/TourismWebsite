package com.xupt.demo1.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xupt.demo1.entity.TrainClasses;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 小关同学
 * @create 2021/11/1
 * 车票查询请求
 */
@Component
public class TicketRequest {

    private static String cityName = null;

    private static Map<String, String> nameCodeMap = new HashMap<>();
    private static Map<String, String> codeNameMap = new HashMap<>();

    /**
     * 获取全国地点的缩写信息
     * @return 返回缩写字符串
     */
    public String readFileContent() {

        //问题：
        //部署到服务器之后找不到路径下的资源
        //解决方法：
        //

        String filePath = "static/ticket/station_name.txt";
        ClassPathResource resource = new ClassPathResource(filePath);
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();

        try {
            //文件读取
            inputStream = resource.getInputStream();
            //两种方法都可以
//            inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }



    /**
     * 获取火车车票信息(注：这是未登录状态只能获取到部分信息)
     * @param date 日期
     * @param start 出发站点
     * @param end 到达站点
     */
    public List<TrainClasses> getTicketInfo(String date, String start, String end){

        List<TrainClasses> list = new ArrayList<>();

        String station_names = null;

        if (cityName==null){
            station_names = readFileContent();
            cityName = station_names;
        }else{
            station_names = cityName;
        }
        Matcher matcher = Pattern.compile("((?<=@)[^@]*)").matcher(station_names);

        if (nameCodeMap.isEmpty() && codeNameMap.isEmpty()){
            //将缩写与中文映射起来
            while (matcher.find()) {
                String tmp = matcher.group();
                String[] contents = tmp.split("\\|");
                String name = contents[1];
                String code = contents[2];
                nameCodeMap.put(name, code);
                codeNameMap.put(code, name);
            }
        }

        if (!nameCodeMap.containsKey(start) || !nameCodeMap.containsKey(end)) {
            try {
                throw new Exception("请确保地名正确！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String url = "https://kyfw.12306.cn/otn/leftTicket/query?" +
                "leftTicketDTO.train_date="+ date +
                "&leftTicketDTO.from_station="+ nameCodeMap.get(start) +
                "&leftTicketDTO.to_station=" + nameCodeMap.get(end) +
                "&purpose_codes=ADULT";

        OkHttpClient client = new OkHttpClient();
        Response response;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.15.2")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "27ef0cbb-e7d7-433b-a36b-ee7392c2df22,3830e5fb-8b7b-41a7-a29d-4a48374234bd")
                .addHeader("Host", "kyfw.12306.cn")
                .addHeader("Cookie", "JSESSIONID=7F26B65CC77BA56C896BE701F23D80D1")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            response = client.newCall(request).execute();
            String jsonStr = response.body().string();
            // 处理json数据
            JSONObject jsonObj = JSON.parseObject(jsonStr);
            JSONArray jsonArr = (JSONArray) ((JSONObject) jsonObj.get("data")).get("result");
            for (Object item : jsonArr.toArray()) {
                TrainClasses trainClasses = new TrainClasses();
                String record = item.toString();
                String[] recordArr = record.split("\\|");

//                System.out.println("--------------------------------------------------------");

                //车次
                trainClasses.setTrainId(recordArr[3]);
//                System.out.println("车次: " + recordArr[3]);

                //出发地
                String startStation = codeNameMap.get(recordArr[6]);
                trainClasses.setStartStation(startStation);
//                System.out.println("出发地: " + startStation);

                //目的地
                String endStation = codeNameMap.get(recordArr[7]);
                trainClasses.setEndStation(endStation);
//                System.out.println("目的地: " + endStation);

                //出发时间
                trainClasses.setStartTime(recordArr[8]);
//                System.out.println("出发时间: " + recordArr[8]);

                //到达时间
                trainClasses.setEndTime(recordArr[9]);
//                System.out.println("到达时间: " + recordArr[9]);

                //总耗时
                trainClasses.setTotalTime(recordArr[10]);
//                System.out.println("历时: " + recordArr[10]);

                //高级软卧
//                System.out.println("高级软卧: " + recordArr[21]);

                //软卧
                if(recordArr[23]==null||recordArr[23].isEmpty()){
                    trainClasses.setSoftSleeper("-");
                }else{
                    trainClasses.setSoftSleeper(recordArr[23]);
                }
//                System.out.println("软卧: " + recordArr[23]);

                //无座
                if (recordArr[26]==null||recordArr[26].isEmpty()){
                    trainClasses.setWithoutSeat("-");
                }else{
                    trainClasses.setWithoutSeat(recordArr[26]);
                }
//                System.out.println("无座: " + recordArr[26]);

                //硬卧
                if (recordArr[28]==null||recordArr[28].isEmpty()){
                    trainClasses.setHardSleeper("-");
                }else{
                    trainClasses.setHardSleeper(recordArr[28]);
                }
//                System.out.println("硬卧: " + recordArr[28]);

                //硬座
                if (recordArr[29]==null||recordArr[29].isEmpty()){
                    trainClasses.setHardSeat("-");
                }else{
                    trainClasses.setHardSeat(recordArr[29]);
                }
//                System.out.println("硬座: " + recordArr[29]);

                //二等座
                if (recordArr[30]==null||recordArr[30].isEmpty()){
                    trainClasses.setSecondClass("-");
                }else{
                    trainClasses.setSecondClass(recordArr[30]);
                }
//                System.out.println("二等座: " + recordArr[30]);

                //一等座
                if (recordArr[31]==null||recordArr[31].isEmpty()){
                    trainClasses.setFirstClass("-");
                }else{
                    trainClasses.setFirstClass(recordArr[31]);
                }
//                System.out.println("一等座: " + recordArr[31]);

                //特等座
                if (recordArr[32]==null||recordArr[32].isEmpty()){
                    trainClasses.setPremierClass("-");
                }else{
                    trainClasses.setPremierClass(recordArr[32]);
                }
//                System.out.println("商务，特等座: " + recordArr[32]);

//                System.out.println("--------------------------------------------------------");
                list.add(trainClasses);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
//        try {
//            getTicketInfo("2021-11-18","北京","上海");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
