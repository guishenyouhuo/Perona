package com.guigui.perona.entity;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 评论对象 comment
 *
 * @author guigui
 * @date 2020-03-26
 */
@Data
@Accessors(chain = true)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 父级ID，给哪个留言进行回复
     */
    private Long pId;

    /**
     * 子级ID，给哪个留言下的回复进行评论
     */
    private Long cId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 昵称
     */
    private String name;

    /**
     * 给谁留言
     */
    private String cName;

    /**
     * 留言时间
     */
    private Date time;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 网址
     */
    private String url;

    /**
     * 分类：0:默认，文章详情页，1:友链页，2:关于页
     */
    private Long sort;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 设备
     */
    private String device;

    /**
     * 地址
     */
    private String address;

    /**
     * 请求参数
     */
    private Map<String, Object> params = Maps.newHashMap();

    // 特别设置set方法
    public void setpId(Long pId) {
        this.pId = pId;
    }

    // 特别设置set方法
    public void setcName(String cName) {
        this.cName = cName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("pId", getPId())
                .append("cId", getCId())
                .append("articleTitle", getArticleTitle())
                .append("articleId", getArticleId())
                .append("name", getName())
                .append("cName", getCName())
                .append("time", getTime())
                .append("content", getContent())
                .append("email", getEmail())
                .append("url", getUrl())
                .append("sort", getSort())
                .append("ip", getIp())
                .append("device", getDevice())
                .append("address", getAddress())
                .toString();
    }
}
