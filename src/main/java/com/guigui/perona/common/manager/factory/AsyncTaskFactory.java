package com.guigui.perona.common.manager.factory;

import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.utils.*;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.service.ILoginLogService;
import com.guigui.perona.service.IUserInfoService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author guigui
 */
public class AsyncTaskFactory {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskFactory.class);

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
//    public static TimerTask syncSessionToDb(final OnlineSession session) {
//        return new TimerTask() {
//            @Override
//            public void run() {
//                UserOnline online = new UserOnline();
//                online.setSessionId(String.valueOf(session.getId()));
//                online.setDeptName(session.getDeptName());
//                online.setLoginName(session.getLoginName());
//                online.setStartTimestamp(session.getStartTimestamp());
//                online.setLastAccessTime(session.getLastAccessTime());
//                online.setExpireTime(session.getTimeout());
//                online.setIpaddr(session.getHost());
//                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
//                online.setBrowser(session.getBrowser());
//                online.setOs(session.getOs());
//                online.setStatus(session.getStatus());
//                online.setSession(session);
//                SpringUtils.getBean(IUserOnlineService.class).saveOnline(online);
//
//            }
//        };
//    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
//    public static TimerTask recordOper(final OperLog operLog) {
//        return new TimerTask() {
//            @Override
//            public void run() {
//                // 远程查询操作地点
//                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
//                SpringUtils.getBean(IOperLogService.class).insertOperlog(operLog);
//            }
//        };
//    }

    /**
     * 更新用户登陆信息
     *
     * @param userInfo 用户信息
     * @return 任务task
     */
    public static TimerTask recordUserInfo(final UserInfo userInfo) {
        String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                // 更新用户信息
                userInfo.setLoginIp(ip);
                userInfo.setLoginDate(DateUtils.getNowDate());
                SpringContextUtils.getBean(IUserInfoService.class).updateUserRecord(userInfo);
            }
        };
    }

    /**
     * 记录登陆日志
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLoginLog(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader(CommonConstants.USER_AGENT));
        final String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getAddress(ip);
                StringBuilder s = new StringBuilder();
                s.append(StringUtils.getBlock(ip));
                s.append(address);
                s.append(StringUtils.getBlock(username));
                s.append(StringUtils.getBlock(status));
                s.append(StringUtils.getBlock(message));
                // 打印信息到日志
                logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                String device = browser + " -- " + os;
                // 封装对象
                LoginLog loginLog = new LoginLog();
                loginLog.setUsername(username);
                loginLog.setIp(ip);
                loginLog.setLocation(address);
                loginLog.setDevice(device);
                loginLog.setMsg(message);
                // 登陆状态
                if (CommonConstants.LOGIN_SUCCESS.equals(status) || CommonConstants.LOGOUT.equals(status)) {
                    loginLog.setStatus(CommonConstants.SUCCESS);
                } else if (CommonConstants.LOGIN_FAIL.equals(status)) {
                    loginLog.setStatus(CommonConstants.FAIL);
                }
                // 插入数据
                SpringContextUtils.getBean(ILoginLogService.class).insertLoginLog(loginLog);
            }
        };
    }
}
