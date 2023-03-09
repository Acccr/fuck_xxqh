package com.acecr.util;

import com.acecr.common.WxConstants;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: acecr
 * @CreateTime: 2022-11-24  22:27
 * @Description: 天行api
 */
public class TianXingUtil {

    /**
     * @description: 获取地区天气
     * @author: acecr
     * @date: 2022/11/24 22:29
     * @param: [type 1:实时天气，7：未来7天天气
     *          areaId:地区ID]
     * @return: JSONObject
     **/
    public static JSONObject getTianQi(String type, String areaId) {
        JSONObject res = new JSONObject();
        String  resmid = Util.post("https://apis.tianapi.com/tianqi/index", "key=da280058caed37325e1cf05011924925&city="+areaId+"&type="+type);
        return JSON.parseObject(resmid).getJSONObject("result");
    }

    /**
     * @description: 获取地区ID
     * @author: acecr
     * @date: 2022/11/24 22:29
     * @param: [area:地名]
     * @return: java.lang.String
     **/
    public static String getAreaId(String area) {
        String res = "";
        String midRes = Util.post("https://apis.tianapi.com/citylookup/index", "key=da280058caed37325e1cf05011924925&area="+area);
        res =  JSON.parseObject(midRes).getJSONObject("result").getJSONArray("list").getJSONObject(0).getString("areaid").replace("CN","");
        return res;
    }


    /** 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getTianQi("1", WxConstants.GD_AREA_ID));
        System.out.println(getAreaId("宣城"));
    }

}
