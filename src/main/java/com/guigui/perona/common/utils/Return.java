package com.guigui.perona.common.utils;

import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.enums.CommonEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @Description: 接口返回类型
 * @Author: guigui
 * @Date: 2019-11-03 13:50
 */

@Builder
@ToString
@AllArgsConstructor
public class Return<T> implements Serializable {

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;

    public Return() {
        super();
    }

    public Return(T data) {
        super();
        this.data = data;
    }

    public Return(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Return(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Return(CommonEnum enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public Return(Throwable e) {
        super();
        this.code = CommonConstants.ERROR;
        this.msg = e.getMessage();
    }

    public Return(String message, Throwable e) {
        super();
        this.msg = message;
        this.code = CommonConstants.ERROR;
    }
}
