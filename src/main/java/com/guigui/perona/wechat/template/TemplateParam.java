package com.guigui.perona.wechat.template;

import lombok.Data;

/**
 * @Description: 模板参数
 * @Author: guigui
 * @Date: 2019-12-11 22:18
 */
@Data
public class TemplateParam {

    //模板消息由于模板选取不同，那么就要封装俩个实体类
    // 参数名称
    private String name;
    // 参数值
    private String value;
    // 颜色
    private String color;

    public TemplateParam() {
        super();
        // TODO Auto-generated constructor stub
    }

    public TemplateParam(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
