package com.acecr.util;

import com.acecr.common.WxConstants;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Util {

    /**
     * 传入三个参数以及微信的token（静态自己设定）验证，
     * @param signature 签名用来核实最后的结果是否一致        
     * @param timestamp 时间标记
     * @param nonce 随机数字标记
     * @return 一个布尔值确定最后加密得到的是否与signature一致
     */
    public static boolean checkSignature(String signature,
            String timestamp,String nonce){
    	String token = WxConstants.TOKEN;
        //将传入参数变成一个String数组然后进行字典排序
        String[] arr=new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        //创建一个对象储存排序后三个String的结合体
        StringBuilder content=new StringBuilder();
        for(int i=0;i<arr.length;i++){
            content.append(arr[i]);
        }
        
        
        //启动sha1加密法的工具
        MessageDigest md=null;
        String tmpStr=null;
        try {
            md=MessageDigest.getInstance("SHA-1");
            //md.digest()方法必须作用于字节数组
            byte[] digest=md.digest(content.toString().getBytes());
            //将字节数组弄成字符串
            tmpStr=byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        content=null;
        
        return tmpStr!=null?tmpStr.equals(signature.toUpperCase()):false;
        
    }
    
    
    /**
     * 将字节加工然后转化成字符串
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest){
        String strDigest="";
        for(int i=0;i<digest.length;i++){
            //将取得字符的二进制码转化为16进制码的的码数字符串
            strDigest+=byteToHexStr(digest[i]);
        }
        return strDigest;
    }
    
    /**
     * 把每个字节加工成一个16位的字符串
     * @param b
     * @return
     */
    public static String byteToHexStr(byte b){
        //转位数参照表
        char[] Digit= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        
        
        //位操作把2进制转化为16进制
        char[] tempArr=new char[2];
        tempArr[0]=Digit[(b>>>4)&0X0F];//XXXX&1111那么得到的还是XXXX
        tempArr[1]=Digit[b&0X0F];//XXXX&1111那么得到的还是XXXX
        
        //得到进制码的字符串
        String s=new String(tempArr);
        return s;
    }
    
    /**
     * 将毫秒字符串转成时间格式yyyy-MM-dd HH:mm:ss
     * @param timeStr
     * @return
     */
    public static String getTimeFormat(String timeStr) {
        long time=Long.parseLong(timeStr)*1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthStr = addZero(month);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = addZero(day);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);//24小时制
        String hourStr = addZero(hour);
        int minute = calendar.get(Calendar.MINUTE);
        String minuteStr = addZero(minute);
        int second = calendar.get(Calendar.SECOND);
        String secondStr =addZero(second);
        return(year + "-" + monthStr  + "-" + dayStr + " "
                + hourStr + ":" + minuteStr + ":" + secondStr);
    }
    
    /**
     * 将毫秒字符串转成时间格式yyyy-MM-dd
     * @param timeStr
     * @return
     */
    public static String getDateFormat(String timeStr) {
        long time=Long.parseLong(timeStr)*1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthStr = addZero(month);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = addZero(day);
        return(year + "-" + monthStr  + "-" + dayStr);
    }
    
    public static String addZero(int param) {
        String paramStr= param<10 ? "0"+param : "" + param ;
        return paramStr;
    }
    
    /*
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     * return 2021-09-23 17:22:12
     */
    public static String getDatemmss() {
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /*
     * 获取当前时间 yyyy-MM-dd
     * return 2021-09-23
     */
    public static String getDate() {
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    
    /*
     * 某个时间加上或减去多少秒之后的时间yyyy-MM-dd HH:mm:ss
     * time单位为秒，正数为加，负数为减
     * date 2021-09-23 15:22:12
     * time 7200
     * return 2021-09-23 17:22:12
     */
    public static String addDatemmss(String date,long time) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date parse = null;
		long now;
	    try {
			parse = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    now = parse.getTime()+time;
        return dateFormat.format(now);
    }
    
    /*
     * 某个时间加上或减去多少秒之后的时间yyyy-MM-dd
     * time单位为秒，正数为加，负数为减
     * date 2021-09-23
     * time 7200
     * return 2021-09-23
     */
    public static String addDate(String date,long time) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date parse = null;
		long now;
	    try {
			parse = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    now = parse.getTime()+time;
        return dateFormat.format(now);
    }
    
    /*
     * 向指定的地址发送post请求,带着data数据
     */
    public static String post(String url,String data) {
    	try {
			URL urlObj = new URL(url);
			// 开连接
			URLConnection connection = urlObj.openConnection();
			// 设置为可发送状态
			connection.setDoOutput(true);
            //connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			OutputStream os = connection.getOutputStream();
			os.write(data.getBytes());
			os.close();
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while((len = is.read(b)) != -1){
				sb.append(new String(b,0,len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /*
     * 向指定的地址发送get请求
     */
    public static String get(String url) {
    	try {
			URL urlObj = new URL(url);
			// 开连接
			URLConnection connection = urlObj.openConnection();
			InputStream is = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			StringBuilder sb = new StringBuilder();
			while((len = is.read(b)) != -1){
				sb.append(new String(b,0,len));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * @description:
     * @author: acecr
     * @date: 2022/11/24 22:09
     * @param: [date]
     * @return: int
     **/
    public static int jiNianRi(String date) {
        int day = 0;
        try {
            long time = System.currentTimeMillis() - new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
            day = (int) (time / 86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
}
