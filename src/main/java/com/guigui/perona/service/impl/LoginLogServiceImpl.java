package com.guigui.perona.service.impl;

import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.mapper.LoginLogMapper;
import com.guigui.perona.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Transactional
    public void saveLog(LoginLog log) {
        loginLogMapper.insert(log);
    }
}
