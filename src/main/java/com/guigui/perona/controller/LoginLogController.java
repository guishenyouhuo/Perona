package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.enums.BusinessType;
import com.guigui.perona.common.utils.PasswordHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.service.ILoginLogService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 登陆日志Controller
 *
 * @author guigui
 * @date 2020-03-31
 */
@Controller
@RequestMapping("/manage/loginLog")
public class LoginLogController extends BaseController {
    private String prefix = "manage/monitor/loginLog";

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private PasswordHelper passwordHelper;

    @GetMapping()
    public String loginLog() {
        return prefix + "/loginLog";
    }

    /**
     * 查询登陆日志列表
     */
    @RequiresPermissions("manage:loginLog:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(LoginLog loginLog) {
        startPage();
        List<LoginLog> list = loginLogService.selectLoginLogList(loginLog);
        return getDataTable(list);
    }

    /**
     * 删除登陆日志
     */
    @RequiresPermissions("manage:loginLog:remove")
    @OperaLog(businessName = "登陆日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(loginLogService.deleteLoginLogByIds(ids));
    }

    @RequiresPermissions("manage:loginLog:clean")
    @OperaLog(businessName = "登陆日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        loginLogService.cleanLoginLog();
        return success();
    }

    @RequiresPermissions("manage:loginLog:unlock")
    @OperaLog(businessName = "账户解锁", businessType = BusinessType.OTHER)
    @PostMapping("/unlock")
    @ResponseBody
    public AjaxResult unlock(String loginName) {
        passwordHelper.clearLoginRecordCache(loginName);
        return success();
    }

}
