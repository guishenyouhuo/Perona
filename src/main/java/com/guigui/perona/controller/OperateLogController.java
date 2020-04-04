package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.OperateLog;
import com.guigui.perona.service.IOperateLogService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 操作日志记录Controller
 *
 * @author guigui
 * @date 2020-04-02
 */
@Controller
@RequestMapping("/manage/operate")
public class OperateLogController extends BaseController {
    private String prefix = "manage/monitor/operatelog";

    @Autowired
    private IOperateLogService operateLogService;

    @GetMapping()
    public String operate() {
        return prefix + "/operatelog";
    }

    /**
     * 查询操作日志记录列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OperateLog operateLog) {
        startPage();
        List<OperateLog> list = operateLogService.selectOperateLogList(operateLog);
        return getDataTable(list);
    }

    /**
     * 新增操作日志记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存操作日志记录
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OperateLog operateLog) {
        return toAjax(operateLogService.insertOperateLog(operateLog));
    }

    /**
     * 修改操作日志记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        OperateLog operateLog = operateLogService.selectOperateLogById(id);
        mmap.put("operateLog", operateLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存操作日志记录
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OperateLog operateLog) {
        return toAjax(operateLogService.updateOperateLog(operateLog));
    }

    /**
     * 删除操作日志记录
     */
    @OperaLog(businessName = "操作日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(operateLogService.deleteOperateLogByIds(ids));
    }

    @GetMapping("/detail/{operateId}")
    public String detail(@PathVariable("operateId") Long operateId, ModelMap mmap) {
        mmap.put("operateLog", operateLogService.selectOperateLogById(operateId));
        return prefix + "/detail";
    }

    @OperaLog(businessName = "操作日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        operateLogService.cleanOperateLog();
        return success();
    }

}
