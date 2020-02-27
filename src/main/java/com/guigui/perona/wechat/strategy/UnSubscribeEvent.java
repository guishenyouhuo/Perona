package com.guigui.perona.wechat.strategy;

import com.guigui.perona.wechat.constants.WeChatConstants;
import com.guigui.perona.wechat.message.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: 取消订阅
 * @Author: guigui
 * @Date: 2019-12-11 17:03
 */
@Service
@Slf4j
public class UnSubscribeEvent extends CommonEventProcess {
    @Override
    public String getEventType() {
        return WeChatConstants.EVENT_TYPE_UNSUBSCRIBE;
    }

    @Override
    public String realProcess(MessageRequest msgReq, String eventKey) {
        log.warn("取消订阅事件暂无任何处理动作！");
        return null;
    }
}
