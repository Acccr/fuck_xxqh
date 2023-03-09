package com.acecr.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @description ：
*/
public class MessageUtil {

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 返回消息类型：图片
     */
    public static final String RESP_MESSAGE_TYPE_Image = "image";

    /**
     * 返回消息类型：语音
     */
    public static final String RESP_MESSAGE_TYPE_Voice = "voice";

    /**
     * 返回消息类型：视频
     */
    public static final String RESP_MESSAGE_TYPE_Video = "video";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：视频
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：VIEW(自定义菜单 URl 视图)
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";

    //@SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        // 将解析结果存储在 HashMap 中
        Map<String, String> map = new HashMap<String, String>();
        // 从 request 中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到 xml 根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

//    public static String textMessageToXml(TextMessage textMessage) {
//        XStream xstream = new XStream(new DomDriver("utf-8"));
//        xstream.alias("xml", textMessage.getClass());
//        return xstream.toXML(textMessage);
//    }
//
//    public static String newsMessageToXml(NewsMessage newsMessage) {
//        xstream.alias("xml", newsMessage.getClass());
//        xstream.alias("item", new Article().getClass());
//        return xstream.toXML(newsMessage);
//    }
//
//    public static String imageMessageToXml(ImageMessage imageMessage) {
//        xstream.alias("xml", imageMessage.getClass());
//        return xstream.toXML(imageMessage);
//    }
//
//    public static String voiceMessageToXml(VoiceMessage voiceMessage) {
//        xstream.alias("xml", voiceMessage.getClass());
//        return xstream.toXML(voiceMessage);
//    }
//
//    public static String videoMessageToXml(VideoMessage videoMessage) {
//        xstream.alias("xml", videoMessage.getClass());
//        return xstream.toXML(videoMessage);
//    }
//
//    public static String musicMessageToXml(MusicMessage musicMessage) {
//        xstream.alias("xml", musicMessage.getClass());
//        return xstream.toXML(musicMessage);
//    }

    /**
     * 对象到 xml 的处理
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有 xml 节点的转换都增加 CDATA 标记
                boolean cdata = true;

                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
    
//    /**
//     * 文件上传
//     * @param filePath路径
//     * @param accessToken
//     * @param type 类型
//     * @return
//     * @throws IOException
//     * @throws NoSuchAlgorithmException
//     * @throws NoSuchProviderException
//     * @throws KeyManagementException
//     */
//    public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
//        File file = new File(filePath);
//        if (!file.exists() || !file.isFile()) {
//            throw new IOException("文件不存在");
//        }
//        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
//        URL urlObj = new URL(url);
//        //连接
//        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
//        con.setRequestMethod("POST"); 
//        con.setDoInput(true);
//        con.setDoOutput(true);
//        con.setUseCaches(false); 
//        //设置请求头信息
//        con.setRequestProperty("Connection", "Keep-Alive");
//        con.setRequestProperty("Charset", "UTF-8");
//        //设置边界
//        String BOUNDARY = "----------" + System.currentTimeMillis();
//        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
//        StringBuilder sb = new StringBuilder();
//        sb.append("--");
//        sb.append(BOUNDARY);
//        sb.append("\r\n");
//        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
//        sb.append("Content-Type:application/octet-stream\r\n\r\n");
//        byte[] head = sb.toString().getBytes("utf-8");
//        //获得输出流
//        OutputStream out = new DataOutputStream(con.getOutputStream());
//        //输出表头
//        out.write(head);
//        //文件正文部分
//        //把文件已流文件的方式 推入到url中
//        DataInputStream in = new DataInputStream(new FileInputStream(file));
//        int bytes = 0;
//        byte[] bufferOut = new byte[1024];
//        while ((bytes = in.read(bufferOut)) != -1) {
//            out.write(bufferOut, 0, bytes);
//        }
//        in.close();
//        //结尾部分
//        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线
//        out.write(foot);
//        out.flush();
//        out.close();
//        StringBuffer buffer = new StringBuffer();
//        BufferedReader reader = null;
//        String result = null;
//        try {
//            //定义BufferedReader输入流来读取URL的响应
//            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line);
//            }
//            if (result == null) {
//                result = buffer.toString();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                reader.close();
//            }
//        }
//        JSONObject jsonObj = JSONObject.fromObject(result);
//        System.out.println(jsonObj);
//        String typeName = "media_id";
//        if(!"image".equals(type)){
//            typeName = type + "_media_id";
//        }
//        String mediaId = jsonObj.getString(typeName);
//        System.err.println("----"+mediaId);
//        return mediaId;
//    }
}