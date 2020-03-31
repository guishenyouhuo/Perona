package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.constants.UserConstants;
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
import com.guigui.perona.entity.DeptInfo;
import com.guigui.perona.service.IDeptInfoService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;

/**
 * 部门Controller
 *
 * @author guigui
 * @date 2020-03-13
 */
@Controller
@RequestMapping("/manage/dept")
public class DeptInfoController extends BaseController {
    private String prefix = "manage/dept";

    @Autowired
    private IDeptInfoService deptInfoService;

    @GetMapping()
    public String dept() {
        return prefix + "/dept";
    }

    /**
     * 查询部门列表
     */
    @PostMapping("/list")
    @ResponseBody
    public List<DeptInfo> list(DeptInfo deptInfo) {
        return deptInfoService.selectDeptInfoList(deptInfo);
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String addView(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        mmap.put("dept", deptInfoService.selectDeptInfoById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DeptInfo deptInfo) {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptInfoService.checkDeptNameUnique(deptInfo))) {
            return error("新增部门'" + deptInfo.getDeptName() + "'失败，部门名称已存在");
        }
        return toAjax(deptInfoService.insertDeptInfo(deptInfo));
    }

    /**
     * 修改部门
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        DeptInfo deptInfo = deptInfoService.selectDeptInfoById(deptId);
        DeptInfo parent = new DeptInfo();
        parent.setDeptName("空");
        if (deptInfo.getParentId() > 0) {
            parent = deptInfoService.selectDeptInfoById(deptInfo.getParentId());
        }
        deptInfo.setParent(parent);
        mmap.put("dept", deptInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存部门
     */
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DeptInfo deptInfo) {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptInfoService.checkDeptNameUnique(deptInfo))) {
            return error("修改部门'" + deptInfo.getDeptName() + "'失败，部门名称已存在");
        } else if (deptInfo.getParentId().equals(deptInfo.getDeptId())) {
            return error("修改部门'" + deptInfo.getDeptName() + "'失败，上级部门不能是自己");
        }
        return toAjax(deptInfoService.updateDeptInfo(deptInfo));
    }

    /**
     * 删除部门
     */
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId) {
        if (deptInfoService.selectParentCount(deptId) > 0) {
            return AjaxResult.warn("存在下级部门,不允许删除");
        }
        if (deptInfoService.checkDeptExistUser(deptId)) {
            return AjaxResult.warn("部门存在用户,不允许删除");
        }
        return toAjax(deptInfoService.deleteDeptInfoById(deptId));
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(RoleInfo roleInfo) {
        return deptInfoService.roleDeptTreeData(roleInfo);
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(DeptInfo deptInfo) {
        return deptInfoService.checkDeptNameUnique(deptInfo);
    }

    /**
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap) {
        mmap.put("dept", deptInfoService.selectDeptInfoById(deptId));
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        return deptInfoService.selectDeptInfoTree(new DeptInfo());
    }

}
