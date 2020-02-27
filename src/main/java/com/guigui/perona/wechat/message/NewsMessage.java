package com.guigui.perona.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

/**
 * @Description: 新闻消息
 * @Author: guigui
 * @Date: 2019-12-10 20:34
 */
@XStreamAlias("NewsMessage")
@Data
public class NewsMessage extends BaseMessage {

    /**
     * 图文消息个数，限制为10条以内
     */
    @XStreamAlias("ArticleCount")
    private Integer articleCount;

    /**
     * 多条图文消息信息，默认第一个item为大图
     */
    @XStreamAlias("Articles")
    private List<NewsArticle> articles;
}
