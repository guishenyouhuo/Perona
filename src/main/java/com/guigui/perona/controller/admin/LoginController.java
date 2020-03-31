package com.guigui.perona.controller.admin;

import com.guigui.perona.common.BaseController;
import com.guigui.perona.common.constants.CommonConstants;
//import com.guigui.perona.common.utils.AddressUtil;
import com.guigui.perona.common.utils.HttpContextUtil;
//import com.guigui.perona.common.utils.IPUtil;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.service.ILoginLogService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 登录处理Controller
 * @Author: guigui
 * @Date: 2019-11-03 15:11
 */
@Slf4j
@RestController
@Api(value = "LoginController", tags = {"登录接口"})
public class LoginController extends BaseController {

    @Autowired
    private ILoginLogService loginLogService;

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     */
//    @PostMapping("/login")
//    public Return login(@RequestParam(value = "username", required = false) String username,
//                        @RequestParam(value = "password", required = false) String password) {
//        Subject subject = getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        try {
//            subject.login(token);
//
//            //记录登录日志
//            LoginLog log = new LoginLog();
//            //获取HTTP请求
//            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
//            String ip = IPUtil.getIpAddr(request);
//            log.setIp(ip);
//            log.setUsername(super.getCurrentUser().getUsername());
//            log.setLocation(AddressUtil.getAddress(ip));
//            log.setCreateTime(LocalDateTime.now());
//            String header = request.getHeader(CommonConstants.USER_AGENT);
//            UserAgent userAgent = UserAgent.parseUserAgentString(header);
//            Browser browser = userAgent.getBrowser();
//            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
//            log.setDevice(browser.getName() + " -- " + operatingSystem.getName());
//            loginLogService.saveLog(log);
//            Map<String, Object> map = new HashMap<>();
//            map.put("token", subject.getSession().getId());
//            map.put("user", this.getCurrentUser());
//            return new Return<>(map);
//        } catch (Exception e) {
//            log.error("登录出现异常！username: {}", username, e);
//            return new Return<>(e);
//        }
//    }

    /**
     * 注销接口
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public Return logout() {
        Subject subject = getSubject();
        subject.logout();
        return new Return();
    }
}
