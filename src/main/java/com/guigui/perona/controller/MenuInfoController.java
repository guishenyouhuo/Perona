package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.enums.BusinessType;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.manage.web.domain.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.guigui.perona.entity.MenuInfo;
import com.guigui.perona.service.IMenuInfoService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;

/**
 * 菜单权限Controller
 *
 * @author guigui
 * @date 2020-03-10
 */
@Controller
@RequestMapping("/manage/menu")
public class MenuInfoController extends BaseController {
    private String prefix = "manage/menu";

    @Autowired
    private IMenuInfoService menuInfoService;

    @GetMapping()
    public String menu() {
        return prefix + "/menu";
    }

    /**
     * 查询菜单权限列表
     */
    @PostMapping("/list")
    @ResponseBody
    public List<MenuInfo> list(MenuInfo menuInfo) {
        return menuInfoService.selectMenuInfoList(menuInfo);
    }

    /**
     * 新增菜单权限
     */
    @GetMapping("/add/{parentId}")
    public String addView(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        MenuInfo menuInfo = null;
        if (0L != parentId) {
            menuInfo = menuInfoService.selectMenuInfoById(parentId);
        } else {
            menuInfo = new MenuInfo();
            menuInfo.setMenuId(0L);
            menuInfo.setMenuName("主目录");
        }
        mmap.put("menu", menuInfo);
        return prefix + "/add";
    }

    /**
     * 校验菜单名称
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(MenuInfo menuInfo) {
        return menuInfoService.checkMenuNameUnique(menuInfo);
    }

    /**
     * 新增保存菜单权限
     */
    @OperaLog(businessName = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MenuInfo menuInfo) {
        if (UserConstants.MENU_NAME_NOT_UNIQUE.equals(menuInfoService.checkMenuNameUnique(menuInfo))) {
            return error("新增菜单'" + menuInfo.getMenuName() + "'失败，菜单名称已存在");
        }
        return toAjax(menuInfoService.insertMenuInfo(menuInfo));
    }

    /**
     * 修改菜单权限
     */
    @GetMapping("/edit/{menuId}")
    public String editView(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        MenuInfo menuInfo = menuInfoService.selectMenuInfoById(menuId);
        // 不是主目录，查询其上级目录
        if (menuInfo.getParentId() > 0L) {
            MenuInfo parentMenu = menuInfoService.selectMenuInfoById(menuInfo.getParentId());
            menuInfo.setParent(parentMenu);
        }
        mmap.put("menu", menuInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存菜单权限
     */
    @OperaLog(businessName = "菜单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MenuInfo menuInfo) {
        if (UserConstants.MENU_NAME_NOT_UNIQUE.equals(menuInfoService.checkMenuNameUnique(menuInfo))) {
            return error("修改菜单'" + menuInfo.getMenuName() + "'失败，菜单名称已存在");
        }
        return toAjax(menuInfoService.updateMenuInfo(menuInfo));
    }

    @OperaLog(businessName = "菜单管理", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{menuId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        if (menuInfoService.selectCountMenuByParentId(menuId) > 0) {
            return AjaxResult.warn("存在子菜单,不允许删除");
        }
        if (menuInfoService.selectCountRoleMenuByMenuId(menuId) > 0) {
            return AjaxResult.warn("菜单已分配,不允许删除");
        }
        return toAjax(menuInfoService.deleteMenuInfoById(menuId));
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuInfoService.selectMenuInfoById(menuId));
        return prefix + "/tree";
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Ztree> menuTreeData() {
        return menuInfoService.generateMenuTree();
    }

    /**
     * 加载角色菜单列表树
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Ztree> roleMenuTreeData(RoleInfo role) {
        return menuInfoService.roleMenuTreeData(role);
    }

}
