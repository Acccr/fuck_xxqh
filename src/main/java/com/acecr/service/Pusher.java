package com.acecr.service;

import cn.hutool.http.HttpUtil;
import com.acecr.common.WxConstants;
import com.acecr.util.BaiduUtil;
import com.acecr.util.TianXingUtil;
import com.acecr.util.Util;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

import java.text.SimpleDateFormat;

/**
 *@ClassName Pusher
 *@Description TODO
 *@Author ydzhao
 *@Date 2022/8/2 
 */
public class Pusher {
    private static String appId = "xxxx";
    private static String secret = "xxx";
 
    public static void push(String openId,String templateId){
        //1，配置
        WxMpDefaultConfigImpl wxStorage = new WxMpDefaultConfigImpl();
        wxStorage.setAppId(WxConstants.APP_ID);
        wxStorage.setSecret(WxConstants.APP_SECRET);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(templateId)
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        JSONObject todayWeather = BaiduUtil.getLocationTianQi("341822");
        String addr = BaiduUtil.getLocation("30.894520","119.444778");

        // 实时天气
        JSONObject now = JSONObject.parseObject(todayWeather.get("now").toString());
        // 今日天气
        JSONObject today = JSONArray.parseArray(todayWeather.get("forecasts").toString()).getJSONObject(0);
        // 明日天气
        JSONObject tomorrow = JSONArray.parseArray(todayWeather.get("forecasts").toString()).getJSONObject(1);
        // 每日英语
        String dailyEnglishUrl = "http://api.tianapi.com/everyday/index?key=" + WxConstants.TX_AK;
        String dailyEnglishStr = HttpUtil.get(dailyEnglishUrl);
        JSONObject dailyEnglishObject = JSONArray.parseArray(JSONObject.parseObject(dailyEnglishStr).get("newslist").toString()).getJSONObject(0);
        // 英文句子
        String english = dailyEnglishObject.get("content").toString();
        // 中文翻译
        String chinese = dailyEnglishObject.get("note").toString();
        templateMessage.addData(new WxMpTemplateData("location", addr, "#9370DB"));
        templateMessage.addData(new WxMpTemplateData("now_temp", now.get("temp").toString(), "#87CEFA"));
        templateMessage.addData(new WxMpTemplateData("now_weather", now.get("text").toString(), "#87CEEB"));
        templateMessage.addData(new WxMpTemplateData("now_wind_dir", now.get("wind_dir").toString(), "#708090"));
        templateMessage.addData(new WxMpTemplateData("now_wind_class", now.get("wind_class").toString(), "#708090"));
        templateMessage.addData(new WxMpTemplateData("now_rh", now.get("rh").toString(), "#778899"));
        String todayWeatherDay = today.get("text_day").toString();
        String todayWeatherNight = today.get("text_night").toString();
        if (todayWeatherDay.equals(todayWeatherNight)) {
            templateMessage.addData(new WxMpTemplateData("today_weather", todayWeatherDay, "#FFC1C1"));
        } else {
            templateMessage.addData(new WxMpTemplateData("today_weather", todayWeatherDay + "转" + todayWeatherNight, "#FFC1C1"));
        }
        templateMessage.addData(new WxMpTemplateData("today_high", today.get("high").toString(), "#CD9B9B"));
        templateMessage.addData(new WxMpTemplateData("today_low", today.get("low").toString(), "#CD9B9B"));
        String tomorrowWeatherDay = tomorrow.get("text_day").toString();
        String tomorrowWeatherNight = tomorrow.get("text_night").toString();
        if (tomorrowWeatherDay.equals(tomorrowWeatherNight)) {
            templateMessage.addData(new WxMpTemplateData("tomorrow_weather", tomorrowWeatherDay, "#DDA0DD"));
        } else {
            templateMessage.addData(new WxMpTemplateData("tomorrow_weather", tomorrowWeatherDay + "转" + tomorrowWeatherNight, "#DDA0DD"));
        }
        templateMessage.addData(new WxMpTemplateData("tomorrow_high", tomorrow.get("high").toString(), "#EE82EE"));
        templateMessage.addData(new WxMpTemplateData("tomorrow_low", tomorrow.get("low").toString(), "#EE82EE"));
        // 相识天数，可以修改为恋爱天数，或者其他纪念意义天数
//        Long 每日英语eetDays = WxMpTemplateDataOpUtils.countDays(WxConstants.MEET_DATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        templateMessage.addData(new WxMpTemplateData("meet_days", String.valueOf(Util.jiNianRi(WxConstants.MEET_DATE)), "#C000C0"));
        templateMessage.addData(new WxMpTemplateData("daily_english_en", english, "#FFCCFF"));
        templateMessage.addData(new WxMpTemplateData("daily_english_cn", chinese, "#CCCCFF"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        push("o2w_56ZKvgqvIRi8aLpXlvvxXm7s","j1w6DAEPXWbZZuhpQ06kTkTNbv5M3oqk8V6N5pjbEJY");
        push("o2w_56U-nWm4tV3ke1Y1svwGnv7I","j1w6DAEPXWbZZuhpQ06kTkTNbv5M3oqk8V6N5pjbEJY");

    }
}