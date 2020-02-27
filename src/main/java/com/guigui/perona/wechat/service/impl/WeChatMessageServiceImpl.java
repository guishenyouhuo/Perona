package com.guigui.perona.wechat.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guigui.perona.wechat.constants.WeChatConstants;
import com.guigui.perona.wechat.message.MessageRequest;
import com.guigui.perona.wechat.message.NewsArticle;
import com.guigui.perona.wechat.message.NewsMessage;
import com.guigui.perona.wechat.message.TextMessage;
import com.guigui.perona.wechat.service.WeChatMessageService;
import com.guigui.perona.wechat.strategy.CommonEventProcess;
import com.guigui.perona.wechat.strategy.factory.EventMsgFactory;
import com.guigui.perona.wechat.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 消息处理实现
 * @Author: guigui
 * @Date: 2019-12-11 11:39
 */
@Service
@Slf4j
public class WeChatMessageServiceImpl implements WeChatMessageService {

    private static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String newMessageRequest(HttpServletRequest request) throws Exception {
        // xml请求解析
        Map<String, String> requestMap = MessageUtil.xmlToMap(request);
        MessageRequest msgReq = new MessageRequest();
        // 发送方帐号（open_id）
        msgReq.setFromUserName(requestMap.get(WeChatConstants.FROM_USER_KEY));
        // 公众帐号
        msgReq.setToUserName(requestMap.get(WeChatConstants.TO_USER_KEY));
        // 消息类型
        msgReq.setMsgType(requestMap.get(WeChatConstants.MSG_TYPE_KEY));
        // 消息内容
        msgReq.setContent(requestMap.get(WeChatConstants.CONTENT_KEY));

        log.info("解析后的请求信息：msgReq: {}", MAPPER.writeValueAsString(msgReq));

        String msgType = msgReq.getMsgType();
        // 接受到文本消息
        if (StringUtils.equals(msgType, WeChatConstants.REQ_MESSAGE_TYPE_TEXT)) {
            //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的 TODO
            /*if(content.equals("xxx")){
            }*/
            if (StringUtils.equals("文章", msgReq.getContent())) {
                NewsMessage message = new NewsMessage();
                message.setToUserName(msgReq.getFromUserName());
                message.setFromUserName(msgReq.getToUserName());
                message.setCreateTime(new Date().getTime());
                message.setMsgType(WeChatConstants.RESP_MESSAGE_TYPE_NEWS);
                message.setArticleCount(1);
                List<NewsArticle> articles = new ArrayList<>();
                NewsArticle article = new NewsArticle();
                article.setTitle("测试文章标题");
                article.setDescription("这是一篇测试文章");
                article.setPicUrl("http://www.perona.top/uploadImg/191204/mysql_keng_1.png");
                article.setUrl("http://www.perona.top/article/11");
                articles.add(article);
                message.setArticles(articles);
                message.setFuncFlag(1);
                return MessageUtil.newsMessageToXml(message);
            }
            //自动回复
            TextMessage text = new TextMessage();
            text.setContent("自动回复内容：" + msgReq.getContent());
            text.setToUserName(msgReq.getFromUserName());
            text.setFromUserName(msgReq.getToUserName());
            text.setCreateTime(new Date().getTime());
            text.setMsgType(msgType);
            return MessageUtil.textMessageToXml(text);
        }

        // 接受到事件类型消息
        if (StringUtils.equals(msgType, WeChatConstants.REQ_MESSAGE_TYPE_EVENT)) {
            // 事件类型
            String eventType = requestMap.get(WeChatConstants.EVENT_KEY);
            // 事件KEY值，与创建自定义菜单时指定的KEY值对应
            String eventKey = requestMap.get(WeChatConstants.EVENT_KEY_KEY);
            CommonEventProcess eventProcess = EventMsgFactory.getEventProcess(eventType);
            if (eventProcess == null) {
                log.warn("暂不支持处理的事件类型：【{}】", eventType);
                return null;
            }
            return eventProcess.realProcess(msgReq, eventKey);
        }
        // 暂时无法解析的消息类型
        log.warn("接收到无法解析的消息类型！msgType：{}", msgType);
        return null;
    }

    public List<String> recursionOpenId(List<String> openIdList,String nextOpenId,WxMpService wxMpService){
        try {
            WxMpUserList openList = wxMpService.getUserService().userList(nextOpenId);
            for(String strId :openList.getOpenids()){
                openIdList.add(strId);
            }
            if(openList.getOpenids().size()>10000){
                recursionOpenId(openIdList,openList.getNextOpenid(),wxMpService);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return openIdList;
    }

}
