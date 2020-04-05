package com.guigui.perona.entity;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 友链对象 friend_link
 *
 * @author guigui
 * @date 2020-03-28
 */
@Data
@Accessors(chain = true)
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 连接名称
     */
    private String name;

    /**
     * 连接URL
     */
    private String url;

    /**
     * 请求参数
     */
    private Map<String, Object> params = Maps.newHashMap();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("url", getUrl())
                .toString();
    }
}
