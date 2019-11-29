package com.guigui.perona.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guigui.perona.common.utils.QueryPage;
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

    IPage<LoginLog> list(LoginLog log, QueryPage queryPage);

    void delete(Long id);

}
