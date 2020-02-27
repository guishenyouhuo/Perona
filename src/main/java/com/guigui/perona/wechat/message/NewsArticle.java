package com.guigui.perona.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description: 图文类型
 * @Author: guigui
 * @Date: 2019-12-10 20:36
 */
@Data
@XStreamAlias("Article")
public class NewsArticle {
    /**
     * 图文消息描述
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 图片链接，支持JPG、PNG格式，<br>
     * 较好的效果为大图640*320，小图80*80
     */
    @XStreamAlias("PicUrl")
    private String picUrl;

    /**
     * 图文消息名称
     */
    @XStreamAlias("Title")
    private String title;

    /**
     * 点击图文消息跳转链接
     */
    @XStreamAlias("Url")
    private String url;
}
