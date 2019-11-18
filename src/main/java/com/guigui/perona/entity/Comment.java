package com.guigui.perona.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID，给哪个留言进行回复
     */
    @TableField("p_id")
    private Long pId;

    /**
     * 子级ID，给哪个留言下的回复进行评论
     */
    @TableField("c_id")
    private Long cId;

    /**
     * 文章标题
     */
    @TableField("article_title")
    private String articleTitle;

    /**
     * 文章ID
     */
    @TableField("article_id")
    private Long articleId;

    /**
     * 昵称
     */
    @TableField("name")
    private String name;

    /**
     * 给谁留言
     */
    @TableField("c_name")
    private String cName;

    /**
     * 留言时间
     */
    @TableField("time")
    private LocalDateTime time;

    /**
     * 留言内容
     */
    @TableField("content")
    private String content;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 网址
     */
    @TableField("url")
    private String url;

    /**
     * 分类：0:默认，文章详情页，1:友链页，2:关于页
     */
    @TableField("sort")
    private Long sort;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 设备
     */
    @TableField("device")
    private String device;

    /**
     * 地址
     */
    @TableField("address")
    private String address;


}
