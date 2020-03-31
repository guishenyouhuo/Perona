package com.guigui.perona.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private String prefix = "manage/loginLog";

    @Autowired
    private ILoginLogService loginLogService;

    @GetMapping()
    public String loginLog() {
        return prefix + "/loginLog";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(LoginLog loginLog) {
        startPage();
        List<LoginLog> list = loginLogService.selectLoginLogList(loginLog);
        return getDataTable(list);
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(LoginLog loginLog) {
        return toAjax(loginLogService.insertLoginLog(loginLog));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        LoginLog loginLog = loginLogService.selectLoginLogById(id);
        mmap.put("loginLog", loginLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(LoginLog loginLog) {
        return toAjax(loginLogService.updateLoginLog(loginLog));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(loginLogService.deleteLoginLogByIds(ids));
    }

}
