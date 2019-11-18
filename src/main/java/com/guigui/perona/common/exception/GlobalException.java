package com.guigui.perona.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 统一异常处理
 * @Author: guigui
 * @Date: 2019-10-30 17:06
 */
public class GlobalException extends RuntimeException {

    @Getter
    @Setter
    private String msg;

    public GlobalException(String message) {
        super(message);
        this.msg = message;
    }
}
