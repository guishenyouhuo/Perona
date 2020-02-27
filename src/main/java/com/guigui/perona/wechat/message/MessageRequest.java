package com.guigui.perona.wechat.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 请求消息
 * @Author: guigui
 * @Date: 2019-12-11 15:52
 */
@Data
public class MessageRequest implements Serializable {

    private static final long serialVersionUID = -7537570155314454131L;

    // 开发者微信号
    private String toUserName;

    // 发送方帐号（一个OpenID）
    private String fromUserName;

    // 消息类型（text/image/location/link）
    private String msgType;

    // 消息内容
    private String content;

}
