package com.guigui.perona.manage.web.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Ztree树结构实体类
 *
 * @author guigui
 */
@Data
public class Ztree implements Serializable {

    private static final long serialVersionUID = -3189246841240825725L;
    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点父ID
     */
    private Long pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点标题
     */
    private String title;

    /**
     * 是否勾选
     */
    private boolean checked = false;

    /**
     * 是否展开
     */
    private boolean open = false;

    /**
     * 是否能勾选
     */
    private boolean nocheck = false;

    /**
     * 自定义pId的get方法，序列化后字段名称为：pId
     */
    public Long getpId() {
        return pId;
    }
}
