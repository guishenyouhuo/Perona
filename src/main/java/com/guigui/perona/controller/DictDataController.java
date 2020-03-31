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
import com.guigui.perona.entity.DictData;
import com.guigui.perona.service.IDictDataService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 字典数据Controller
 *
 * @author guigui
 * @date 2020-03-13
 */
@Controller
@RequestMapping("/manage/dict/data")
public class DictDataController extends BaseController {
    private String prefix = "manage/dict/data";

    @Autowired
    private IDictDataService dictDataService;

    @GetMapping()
    public String dict() {
        return prefix + "/data";
    }

    /**
     * 查询字典数据列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DictData dictData) {
        startPage();
        List<DictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /**
     * 新增字典数据
     */
    @GetMapping("/add/{dictType}")
    public String addView(@PathVariable("dictType") String dictType, ModelMap mmap) {
        mmap.put("dictType", dictType);
        return prefix + "/add";
    }

    /**
     * 新增保存字典数据
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DictData dictData) {
        return toAjax(dictDataService.insertDictData(dictData));
    }

    /**
     * 修改字典数据
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
        DictData dictData = dictDataService.selectDictDataById(dictCode);
        mmap.put("dictData", dictData);
        return prefix + "/edit";
    }

    /**
     * 修改保存字典数据
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DictData dictData) {
        return toAjax(dictDataService.updateDictData(dictData));
    }

    /**
     * 删除字典数据
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(dictDataService.deleteDictDataByIds(ids));
    }

}
