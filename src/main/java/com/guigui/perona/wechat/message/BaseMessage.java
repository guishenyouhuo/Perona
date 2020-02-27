package com.guigui.perona.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Description: 消息基类（普通用户 -> 公众帐号）
 * @Author: guigui
 * @Date: 2019-12-10 20:28
 */
@Data
public class BaseMessage {

    // 开发者微信号
    @XStreamAlias("ToUserName")
    private String toUserName;
    // 发送方帐号（一个OpenID）
    @XStreamAlias("FromUserName")
    private String fromUserName;
    // 消息创建时间 （整型）
    @XStreamAlias("CreateTime")
    private long createTime;
    // 消息类型（text/image/location/link）
    @XStreamAlias("MsgType")
    private String msgType;
    // 消息id，64位整型
    @XStreamAlias("MsgId")
    private long msgId;
    /**
     * 位0x0001被标志时，星标刚收到的消息
     */
    @XStreamAlias("FuncFlag")
    private int funcFlag;

}
