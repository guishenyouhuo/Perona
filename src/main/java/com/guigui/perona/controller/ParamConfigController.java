package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.ParamConfig;
import com.guigui.perona.service.IParamConfigService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 参数配置Controller
 *
 * @author guigui
 * @date 2020-03-13
 */
@Controller
@RequestMapping("/manage/config")
public class ParamConfigController extends BaseController {
    private String prefix = "manage/config";

    @Autowired
    private IParamConfigService paramConfigService;

    @GetMapping()
    public String param() {
        return prefix + "/config";
    }

    /**
     * 查询参数配置列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ParamConfig paramConfig) {
        startPage();
        List<ParamConfig> list = paramConfigService.selectParamConfigList(paramConfig);
        return getDataTable(list);
    }

    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String addView() {
        return prefix + "/add";
    }

    /**
     * 新增保存参数配置
     */
    @OperaLog(businessName = "系统参数", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ParamConfig paramConfig) {
        if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(paramConfigService.checkConfigKeyUnique(paramConfig))) {
            return error("新增参数'" + paramConfig.getConfigName() + "'失败，参数键名已存在");
        }
        return toAjax(paramConfigService.insertParamConfig(paramConfig));
    }

    /**
     * 修改参数配置
     */
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Long configId, ModelMap mmap) {
        ParamConfig paramConfig = paramConfigService.selectParamConfigById(configId);
        mmap.put("paramConfig", paramConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存参数配置
     */
    @OperaLog(businessName = "系统参数", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ParamConfig paramConfig) {
        if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(paramConfigService.checkConfigKeyUnique(paramConfig))) {
            return error("修改参数'" + paramConfig.getConfigName() + "'失败，参数键名已存在");
        }
        return toAjax(paramConfigService.updateParamConfig(paramConfig));
    }

    /**
     * 删除参数配置
     */
    @OperaLog(businessName = "系统参数", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(paramConfigService.deleteParamConfigByIds(ids));
    }

    /**
     * 校验参数键名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public String checkConfigKeyUnique(ParamConfig paramConfig) {
        return paramConfigService.checkConfigKeyUnique(paramConfig);
    }

}
