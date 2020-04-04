package com.guigui.perona.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.enums.BusinessStatus;
import com.guigui.perona.common.manager.AsyncManager;
import com.guigui.perona.common.manager.factory.AsyncTaskFactory;
import com.guigui.perona.common.utils.*;
import com.guigui.perona.entity.OperateLog;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.IOperateLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志切面
 *
 * @author guigui
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private IOperateLogService logService;

    @Pointcut("@annotation(com.guigui.perona.common.aspect.annotation.OperaLog)")
    public void pointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "pointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    private void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            OperaLog logAnnotation = getLogAnnotation(joinPoint);
            if (logAnnotation == null) {
                return;
            }

            // 获取当前的用户
            UserInfo currentUser = ShiroUtils.getUserInfo();

            HttpServletRequest servletRequest = ServletUtils.getRequest();

            // 设置日志信息
            OperateLog operateLog = new OperateLog();
            operateLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IPUtils.getIpAddr(servletRequest);
            operateLog.setIpAddr(ip);
            // 返回参数
            operateLog.setJsonResult(JSONObject.toJSONString(jsonResult));

            operateLog.setOperateUrl(servletRequest.getRequestURI());
            if (currentUser != null) {
                operateLog.setOperator(currentUser.getUsername());
                if (currentUser.getDeptInfo() != null
                        && StringUtils.isNotEmpty(currentUser.getDeptInfo().getDeptName())) {
                    operateLog.setDeptName(currentUser.getDeptInfo().getDeptName());
                }
            }

            if (e != null) {
                operateLog.setStatus(BusinessStatus.FAIL.ordinal());
                operateLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 500));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operateLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operateLog.setReqMethod(servletRequest.getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(logAnnotation, operateLog);
            // 保存数据库
            AsyncManager.async().execute(AsyncTaskFactory.recordOperate(operateLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param logAnnotation 日志
     * @param operateLog    操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(OperaLog logAnnotation, OperateLog operateLog) throws Exception {
        // 设置action动作
        operateLog.setBizType(logAnnotation.businessType().ordinal());
        // 设置标题
        operateLog.setBizName(logAnnotation.businessName());
        // 设置操作人类别
        operateLog.setOperateType(logAnnotation.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (logAnnotation.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operateLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operateLog 日志
     */
    private void setRequestValue(OperateLog operateLog) {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operateLog.setOperateParam(StringUtils.substring(params, 0, 500));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperaLog getLogAnnotation(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(OperaLog.class);
        }
        return null;
    }
}
