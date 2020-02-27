package com.guigui.perona.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description: 文本消息
 * @Author: guigui
 * @Date: 2019-12-10 20:37
 */
@Data
@XStreamAlias("TextMessage")
public class TextMessage extends BaseMessage {
    // 消息内容
    @XStreamAlias("Content")
    private String content;
}
