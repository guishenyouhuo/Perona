package com.guigui.perona.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.mapper.LoginLogMapper;
import com.guigui.perona.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
    public IPage<LoginLog> list(LoginLog log, QueryPage queryPage) {
        IPage<LoginLog> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<LoginLog> queryWrapper = new QueryWrapper<LoginLog>().lambda();
        queryWrapper.like(StringUtils.isNotBlank(log.getLocation()), LoginLog::getLocation, log.getLocation());
        queryWrapper.orderByDesc(LoginLog::getCreateTime);
        if (StringUtils.isNotBlank(log.getFiledTime())) {
            String[] split = log.getFiledTime().split(",");
            queryWrapper.gt(LoginLog::getCreateTime, split[0]);
            queryWrapper.lt(LoginLog::getCreateTime, split[1]);
        }
        return loginLogMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        loginLogMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void saveLog(LoginLog log) {
        loginLogMapper.insert(log);
    }
}
