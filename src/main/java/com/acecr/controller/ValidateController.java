package com.acecr.controller;

import com.acecr.util.MessageUtil;
import com.acecr.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Slf4j
public class ValidateController {

	public static final long serialVersionUID = 1L;

	/*
	 * 启动验证
	 */
    @RequestMapping(value = "/wxtest", method = RequestMethod.GET)
    @ResponseBody  
    public String check(HttpServletRequest request,HttpServletResponse response){
    	String signature = request.getParameter("signature");
    	String timestamp = request.getParameter("timestamp");
    	String nonce = request.getParameter("nonce");
    	String echostr = request.getParameter("echostr");
    	if(Util.checkSignature(signature, timestamp, nonce)) {
    		log.info("*****校验成功****");
    		return echostr;
    	}else {
            log.error("*****校验失败****");
    		return null;
    	}
    }
    
    /*
	 * 微信后台入口
	 */
    @RequestMapping(value = "/wxtest", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws Exception{
        try{
        	Map<String, String> map=MessageUtil.parseXml(request);
            System.out.println(map);
            System.out.println("*****FromUserName:"+map.get("FromUserName"));
            System.out.println("*****ToUserName:"+map.get("ToUserName"));
            String visitdate = Util.getTimeFormat(map.get("CreateTime"));
            map.put("visitdate", visitdate);
            System.out.println("*****CreateTime:"+visitdate);
            System.out.println("*****MsgType:"+map.get("MsgType"));
            //是否需要返回消息
       }catch(Exception e){
           System.out.println(e);;
       }
    }

}