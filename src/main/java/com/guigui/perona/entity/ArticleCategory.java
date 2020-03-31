package com.guigui.perona.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文章&&分类关联对象 article_category
 *
 * @author guigui
 * @date 2020-03-26
 */
@Data
@Accessors(chain = true)
public class ArticleCategory implements Serializable {

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
     * 分类ID
     */
    private Long categoryId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("articleId", getArticleId())
                .append("categoryId", getCategoryId())
                .toString();
    }
}
