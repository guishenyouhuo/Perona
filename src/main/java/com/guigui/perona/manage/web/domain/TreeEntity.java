package com.guigui.perona.manage.web.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Tree基类
 *
 * @author guigui
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
public class TreeEntity extends BaseEntity {

    private static final long serialVersionUID = 125771540456754528L;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 祖级列表
     */
    private String ancestors;

}