package com.guigui.perona.wechat.utils;

import com.guigui.perona.wechat.constants.WeChatConstants;
import com.guigui.perona.wechat.message.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 消息处理
 * @Author: guigui
 * @Date: 2019-12-10 20:17
 */
public class MessageUtil {

    /**
     * xml转换为map
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * @param @param  request
     * @param @return
     * @param @throws Exception
     * @Description: 解析微信发来的请求（XML）
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.processAnnotations(textMessage.getClass());
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * @param @param  newsMessage
     * @param @return
     * @Description: 图文消息对象转换成xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.processAnnotations(newsMessage.getClass());
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", NewsArticle.class);
        return xstream.toXML(newsMessage);
    }

    /**
     * @param @param  imageMessage
     * @param @return
     * @Description: 图片消息对象转换成xml
     */
    public static String imageMessageToXml(ImageMessage imageMessage) {
        xstream.processAnnotations(imageMessage.getClass());
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * @param @param  voiceMessage
     * @param @return
     * @Description: 语音消息对象转换成xml
     */
    public static String voiceMessageToXml(VoiceMessage voiceMessage) {
        xstream.processAnnotations(voiceMessage.getClass());
        xstream.alias("xml", voiceMessage.getClass());
        return xstream.toXML(voiceMessage);
    }

    /**
     * @param @param  videoMessage
     * @param @return
     * @Description: 视频消息对象转换成xml
     */
    public static String videoMessageToXml(VideoMessage videoMessage) {
        xstream.processAnnotations(videoMessage.getClass());
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }

    /**
     * @param @param  musicMessage
     * @param @return
     * @Description: 音乐消息对象转换成xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.processAnnotations(musicMessage.getClass());
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

//    public static BaseMessage parseToBaseMessage(HttpServletRequest request) throws Exception {
//        // xml请求解析
//        Map<String, String> requestMap = xmlToMap(request);
//        BaseMessage baseMessage = new BaseMessage();
//        // 发送方帐号（open_id）
//        baseMessage.setFromUserName(requestMap.get(WeChatConstants.FROM_USER_KEY));
//        // 公众帐号
//        baseMessage.setToUserName(requestMap.get(WeChatConstants.TO_USER_KEY));
//        // 消息类型
//        baseMessage.setMsgType(requestMap.get(WeChatConstants.MSG_TYPE_KEY));
//        // 消息内容
//        baseMessage.srequestMap.get(WeChatConstants.CONTENT_KEY);
//    }

    /**
     * 对象到xml的处理
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
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

    public static void main(String[] args) {
        TextMessage message = new TextMessage();
        message.setContent("你好");
        message.setCreateTime(System.currentTimeMillis());
        message.setFromUserName("perona");
        message.setFuncFlag(1);
        message.setMsgId(1234567890);
        message.setMsgType("text");
        message.setToUserName("guigui");
        String str = textMessageToXml(message);
        System.out.println(str);

        String xmlStr = "<xml>\n" +
                "  <ToUserName><![CDATA[guigui]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[perona]]></FromUserName>\n" +
                "  <CreateTime><![CDATA[1576032489718]]></CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <MsgId><![CDATA[1234567890]]></MsgId>\n" +
                "  <FuncFlag><![CDATA[1]]></FuncFlag>\n" +
                "  <Content><![CDATA[你好]]></Content>\n" +
                "</xml>";
//        Map<String, String> xmlMap = xmlToMap()
    }
}
