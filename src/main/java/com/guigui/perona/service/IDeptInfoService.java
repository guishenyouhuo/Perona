package com.guigui.perona.service;

import com.guigui.perona.entity.DeptInfo;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.manage.web.domain.Ztree;

import java.util.List;

/**
 * 部门Service接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface IDeptInfoService {
    /**
     * 查询部门
     *
     * @param deptId 部门ID
     * @return 部门
     */
    DeptInfo selectDeptInfoById(Long deptId);

    /**
     * 查询部门列表
     *
     * @param deptInfo 部门
     * @return 部门集合
     */
    List<DeptInfo> selectDeptInfoList(DeptInfo deptInfo);

    /**
     * 新增部门
     *
     * @param deptInfo 部门
     * @return 结果
     */
    int insertDeptInfo(DeptInfo deptInfo);

    /**
     * 修改部门
     *
     * @param deptInfo 部门
     * @return 结果
     */
    int updateDeptInfo(DeptInfo deptInfo);

    /**
     * 批量删除部门
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDeptInfoByIds(String ids);

    /**
     * 删除部门信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptInfoById(Long deptId);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleInfo 角色对象
     * @return 菜单列表
     */
    List<Ztree> roleDeptTreeData(RoleInfo roleInfo);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptInfo 部门信息
     * @return 结果
     */
    String checkDeptNameUnique(DeptInfo deptInfo);

    /**
     * 查询部门管理树
     *
     * @param deptInfo 部门信息
     * @return 所有部门信息
     */
    List<Ztree> selectDeptInfoTree(DeptInfo deptInfo);

    /**
     * 查询父部门数
     *
     * @param parentId 父部门ID
     * @return 结果
     */
    int selectParentCount(Long parentId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkDeptExistUser(Long deptId);

}
