package com.guigui.perona.entity;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 分类对象 category
 *
 * @author guigui
 * @date 2020-03-25
 */
@Data
@Accessors(chain = true)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 请求参数
     */
    private Map<String, Object> params = Maps.newHashMap();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .toString();
    }
}
