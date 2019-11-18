package com.guigui.perona.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 云存储对象
 * @Author: guigui
 * @Date: 2019-11-15 23:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudEntity implements Serializable {

    private String key; //对象KEY
    private String name; //对象名称
    private String type; //对象类型
    private long size; //对象大小
    private String url; //对象链接
}