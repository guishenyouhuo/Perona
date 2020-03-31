package com.guigui.perona.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文章对象 article
 *
 * @author guigui
 * @date 2020-03-25
 */
@Data
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面图片
     */
    private String cover;

    /**
     * 作者
     */
    private String author;

    /**
     * 内容
     */
    private String content;

    /**
     * 内容-Markdown
     */
    private String contentMd;

    /**
     * 分类
     */
    private String category;

    /**
     * 来源
     */
    private String origin;

    /**
     * 状态
     */
    private String state;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 上次修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 类型， 0原创 1转载
     */
    private Long type;

    private List<ArtTag> artTags;

    private List<String> tagNames;

    private String[] tagIds;

    /**
     * 分类名称
     */
    private String categoryName;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("title", getTitle())
                .append("cover", getCover())
                .append("author", getAuthor())
                .append("content", getContent())
                .append("contentMd", getContentMd())
                .append("category", getCategory())
                .append("origin", getOrigin())
                .append("state", getState())
                .append("publishTime", getPublishTime())
                .append("editTime", getEditTime())
                .append("createTime", getCreateTime())
                .append("type", getType())
                .toString();
    }
}
