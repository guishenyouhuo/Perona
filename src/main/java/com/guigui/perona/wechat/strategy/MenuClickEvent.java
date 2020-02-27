package com.guigui.perona.wechat.strategy;

import com.guigui.perona.wechat.constants.WeChatConstants;
import com.guigui.perona.wechat.message.MessageRequest;
import com.guigui.perona.wechat.message.TextMessage;
import com.guigui.perona.wechat.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: 自定义菜单点击事件
 * @Author: guigui
 * @Date: 2019-12-11 16:55
 */
@Service
@Slf4j
public class MenuClickEvent extends CommonEventProcess {
    @Override
    public String getEventType() {
        return WeChatConstants.EVENT_TYPE_CLICK;
    }

    @Override
    public String realProcess(MessageRequest msgReq, String eventKey) {

        if (eventKey.equals("return_content")) {
            TextMessage text = new TextMessage();
            text.setContent("赞赞赞");
            text.setToUserName(msgReq.getFromUserName());
            text.setFromUserName(msgReq.getToUserName());
            text.setCreateTime(new Date().getTime());
            text.setMsgType(WeChatConstants.RESP_MESSAGE_TYPE_TEXT);
            return MessageUtil.textMessageToXml(text);
        }
        return null;
    }
}
