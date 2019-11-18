package com.guigui.perona.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.OperateLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
public interface IOperateLogService extends IService<OperateLog> {

    IPage<OperateLog> list(OperateLog log, QueryPage queryPage);

    void delete(Long id);

    @Async
    void saveLog(ProceedingJoinPoint proceedingJoinPoint, OperateLog log) throws JsonProcessingException;
}
