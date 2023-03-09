package com.acecr.controller;

import com.acecr.common.WxConstants;
import com.acecr.service.Pusher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: acecr
 * @CreateTime: 2022-11-24  20:44
 * @Description: 定时任务
 */
@RestController
@EnableScheduling
@Slf4j
public class TimedTaskController {

    /**
     * 给特殊的人发早安（SPECIAL_MORNING模板）
     */
    @PostMapping("/executeSpecialMorningTask")
    @Scheduled(cron="0 0 7 * * ?")
    private void executeSpecialMorningTask() {
        Pusher.push("o2w_56ZKvgqvIRi8aLpXlvvxXm7s","j1w6DAEPXWbZZuhpQ06kTkTNbv5M3oqk8V6N5pjbEJY");
        Pusher.push("o2w_56U-nWm4tV3ke1Y1svwGnv7I","j1w6DAEPXWbZZuhpQ06kTkTNbv5M3oqk8V6N5pjbEJY");
    }

    /**
     * 给除了特殊的人以外的人发早安，（COMMON_MORNING模板）
     */
//    @PostMapping("/executeCommonMorningTask")
//    @Scheduled(cron = "0 0 6 * * ?")
//    private void executeCommonMorningTask() {
//        wxPublisher.inform(WxTemplateType.COMMON_MORNING);
//    }

    /**
     * 一个小时获取一次accessToken
     * 记录 accessToken 地址ID等信息
     */

    @Scheduled(fixedRate = 60 * 60 * 1000, initialDelay = 0)
    private void acquireAccessToken() {
//        WxConstants.accessToken = WxOpUtils.getAccessToken();
        log.info(">>> update access_token at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date()) + " --------> " + WxConstants.accessToken);
    }
}
