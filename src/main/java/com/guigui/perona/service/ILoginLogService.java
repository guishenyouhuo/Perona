package com.guigui.perona.service;

import com.guigui.perona.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
public interface ILoginLogService extends IService<LoginLog> {

    void saveLog(LoginLog log);

}
