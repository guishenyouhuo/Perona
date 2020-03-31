package com.guigui.perona.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.HttpContextUtil;
import com.guigui.perona.common.utils.IPUtils;
import com.guigui.perona.entity.OperateLog;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.IOperateLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 操作日志切面
 * @Author: guigui
 * @Date: 2019-11-15 22:54
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private IOperateLogService logService;

    @Pointcut("@annotation(com.guigui.perona.common.annotation.Log)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws JsonProcessingException {
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new GlobalException(throwable.getMessage());
        }
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (userInfo != null) {
            long beginTime = System.currentTimeMillis();
            //获取Request请求
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            //设置IP地址
            String ip = IPUtils.getIpAddr(request);
            //记录时间（毫秒）
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            OperateLog log = new OperateLog();
            log.setIp(ip);
            log.setTime(time);
            logService.saveLog(proceedingJoinPoint, log);
            log.setUsername(userInfo.getUsername());
        }
        return result;
    }
}
