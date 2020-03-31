package com.guigui.perona.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 标签对象 art_tag
 *
 * @author guigui
 * @date 2020-03-25
 */
@Data
@Accessors(chain = true)
public class ArtTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 文章id
     */
    private Long articleId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .toString();
    }
}
