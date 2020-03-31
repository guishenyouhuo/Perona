package com.guigui.perona.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文章&&标签关联对象 article_tag
 *
 * @author guigui
 * @date 2020-03-26
 */
@Data
@Accessors(chain = true)
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标签ID
     */
    private Long tagId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("articleId", getArticleId())
                .append("tagId", getTagId())
                .toString();
    }
}
