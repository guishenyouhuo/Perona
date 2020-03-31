package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.manage.web.domain.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.DictType;
import com.guigui.perona.service.IDictTypeService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 字典类型Controller
 *
 * @author guigui
 * @date 2020-03-13
 */
@Controller
@RequestMapping("/manage/dict")
public class DictTypeController extends BaseController {
    private String prefix = "manage/dict/type";

    @Autowired
    private IDictTypeService dictTypeService;

    @GetMapping()
    public String dict() {
        return prefix + "/type";
    }

    /**
     * 查询字典类型列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DictType dictType) {
        startPage();
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String addView() {
        return prefix + "/add";
    }

    /**
     * 新增保存字典类型
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DictType dictType) {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dictType))) {
            return error("新增字典'" + dictType.getDictName() + "'失败，字典类型已存在");
        }
        return toAjax(dictTypeService.insertDictType(dictType));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        DictType dictType = dictTypeService.selectDictTypeById(dictId);
        mmap.put("dictType", dictType);
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DictType dictType) {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dictType))) {
            return error("修改字典'" + dictType.getDictName() + "'失败，字典类型已存在");
        }
        return toAjax(dictTypeService.updateDictType(dictType));
    }

    /**
     * 删除字典类型
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(dictTypeService.deleteDictTypeByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验字典类型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public String checkDictTypeUnique(DictType dictType) {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

    /**
     * 查询字典详细
     */
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "manage/dict/data/data";
    }

    /**
     * 选择字典树
     */
    @GetMapping("/selectDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType, ModelMap mmap) {
        mmap.put("columnId", columnId);
        mmap.put("dict", dictTypeService.selectDictTypeByType(dictType));
        return prefix + "/tree";
    }

    /**
     * 加载字典列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        return dictTypeService.selectDictTree(new DictType());
    }

}
