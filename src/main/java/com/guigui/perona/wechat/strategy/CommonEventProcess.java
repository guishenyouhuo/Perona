package com.guigui.perona.wechat.strategy;

import com.guigui.perona.wechat.message.MessageRequest;

/**
 * @Description: 通用事件处理
 * @Author: guigui
 * @Date: 2019-12-11 15:34
 */
public abstract class CommonEventProcess {

    // 获取事件类型
    public abstract String getEventType();

    /**
     * 事件消息处理
     * @param msgReq
     * @return
     */
    public abstract String realProcess(MessageRequest msgReq, String eventKey);
}
