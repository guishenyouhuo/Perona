package com.guigui.perona.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 封面图片
     */
    @TableField("cover")
    private String cover;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 内容-Markdown
     */
    @TableField("content_md")
    private String contentMd;

    /**
     * 分类
     */
    @TableField("category")
    private String category;

    /**
     * 来源
     */
    @TableField("origin")
    private String origin;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 上次修改时间
     */
    @TableField("edit_time")
    private LocalDateTime editTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 类型， 0原创 1转载
     */
    @TableField("type")
    private Integer type;

    @TableField(exist = false)
    private List<ArtTag> artTags;

    @TableField(exist = false)
    private List<String> tagNames;

    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String categoryName;

}
