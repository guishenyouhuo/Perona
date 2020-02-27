package com.guigui.perona.wechat.strategy;

import com.guigui.perona.wechat.constants.WeChatConstants;
import com.guigui.perona.wechat.message.MessageRequest;
import com.guigui.perona.wechat.message.NewsArticle;
import com.guigui.perona.wechat.message.NewsMessage;
import com.guigui.perona.wechat.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 订阅事件
 * @Author: guigui
 * @Date: 2019-12-11 16:09
 */
@Service
@Slf4j
public class SubscribeEvent extends CommonEventProcess {
    @Override
    public String getEventType() {
        return WeChatConstants.EVENT_TYPE_SUBSCRIBE;
    }

    @Override
    public String realProcess(MessageRequest msgReq, String eventKey) {
        // 文本消息
        /*TextMessage text = new TextMessage();
        text.setContent("我不管，我最美！！");
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName);
        text.setCreateTime(new Date().getTime());
        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        return MessageUtil.textMessageToXml(text);*/

        // 对图文消息
        NewsMessage newMsg = new NewsMessage();
        newMsg.setToUserName(msgReq.getFromUserName());
        newMsg.setFromUserName(msgReq.getToUserName());
        newMsg.setCreateTime(new Date().getTime());
        newMsg.setMsgType(WeChatConstants.RESP_MESSAGE_TYPE_NEWS);
        newMsg.setFuncFlag(0);
        List<NewsArticle> articleList = new ArrayList<>();

        NewsArticle article = new NewsArticle();
        article.setTitle("我是一个图文");
        article.setDescription("我是描述信息");
        article.setPicUrl("https://sfault-avatar.b0.upaiyun.com/110/007/1100070317-5abcb09d42224_huge256");
        article.setUrl("https://segmentfault.com/u/panzi_5abcaf30a5e6b");
        articleList.add(article);
        // 设置图文消息个数
        newMsg.setArticleCount(articleList.size());
        // 设置图文消息包含的图文集合
        newMsg.setArticles(articleList);
        // 将图文消息对象转换成xml字符串
        return MessageUtil.newsMessageToXml(newMsg);
    }
}
