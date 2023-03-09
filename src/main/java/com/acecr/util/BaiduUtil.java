package com.acecr.util;

import cn.hutool.http.HttpUtil;
import com.acecr.common.WxConstants;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * @Author: acecr
 * @CreateTime: 2022-11-26  21:43
 * @Description:百度api
 */
public class BaiduUtil {

    /**
     * @description: 获取地址
     * @author: acecr
     * @date: 2022/11/24 22:29
     * @param: [latitude:经度;longitude:纬度]
     * @return: java.lang.String
     **/
    public static String getLocation(String latitude, String longitude) {
        String res = new String();
        // 根据经纬度获取地址的url
        String searchAddressUrl = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=" + WxConstants.BAIDU_MAP_AK +
                "&output=json&coordtype=wgs84ll&location=" + latitude + "," + longitude;
        String  resmid = HttpUtil.get(searchAddressUrl);
        String result = JSONObject.parseObject(resmid).get("result").toString();
        JSONObject resultObject = JSONObject.parseObject(result);
        // 详细地址
        String address = resultObject.get("formatted_address").toString();
        return address;
    }

    /**
     * @description: 获取地址
     * @author: acecr
     * @date: 2022/11/24 22:29
     * @param: [latitude:经度;longitude:纬度]
     * @return: JSONObject
     **/
    public static JSONObject getLocationTianQi(String areaId) {
        JSONObject res = new JSONObject();
        // 获取天气的url
        String weatherUrl = "https://api.map.baidu.com/weather/v1/?district_id=" + areaId + "&data_type=all&ak=" + WxConstants.BAIDU_MAP_AK;
        // 天气信息json格式
        String weatherStr = HttpUtil.get(weatherUrl);
        System.out.println(weatherStr);
        res = JSONObject.parseObject(JSONObject.parseObject(weatherStr).get("result").toString());
        return res;
    }



    /** 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getLocation("30.894520","119.444778"));
        System.out.println(getLocationTianQi("341822"));
    }

}
