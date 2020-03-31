package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.constants.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.Category;
import com.guigui.perona.service.ICategoryService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

/**
 * 分类Controller
 * 
 * @author guigui
 * @date 2020-03-26
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryController extends BaseController {
    private String prefix = "manage/blog/category";

    @Autowired
    private ICategoryService categoryService;

    @GetMapping()
    public String category() {
        return prefix + "/category";
    }

    /**
     * 查询分类列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Category category) {
        startPage();
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }

    /**
     * 新增分类
     */
    @GetMapping("/add")
    public String addView() {
        return prefix + "/add";
    }

    /**
     * 新增保存分类
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Category category) {
        if (UserConstants.NOT_UNIQUE.equals(categoryService.checkCategoryUnique(category))) {
            return error("新增分类'" + category.getName() + "'失败，分类名称已存在");
        }
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改分类
     */
    @GetMapping("/edit/{id}")
    public String editView(@PathVariable("id") Long id, ModelMap mmap) {
        Category category = categoryService.selectCategoryById(id);
        mmap.put("category", category);
        return prefix + "/edit";
    }

    /**
     * 修改保存分类
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Category category) {
        if (UserConstants.NOT_UNIQUE.equals(categoryService.checkCategoryUnique(category))) {
            return error("修改分类'" + category.getName() + "'失败，分类名称已存在");
        }
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除分类
     */
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(categoryService.deleteCategoryByIds(ids));
    }

    /**
     * 校验分类名称
     */
    @PostMapping("/checkCategoryUnique")
    @ResponseBody
    public String checkCategoryUnique(Category category) {
        return categoryService.checkCategoryUnique(category);
    }

}
