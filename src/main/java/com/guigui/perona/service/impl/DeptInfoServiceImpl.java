package com.guigui.perona.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.guigui.perona.common.aspect.annotation.DataScope;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.manage.web.domain.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.DeptInfoMapper;
import com.guigui.perona.entity.DeptInfo;
import com.guigui.perona.service.IDeptInfoService;
import com.guigui.perona.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 部门Service业务层处理
 *
 * @author guigui
 * @date 2020-03-13
 */
@Service
public class DeptInfoServiceImpl implements IDeptInfoService {

    @Autowired
    private DeptInfoMapper deptInfoMapper;

    /**
     * 查询部门
     *
     * @param deptId 部门ID
     * @return 部门
     */
    @Override
    public DeptInfo selectDeptInfoById(Long deptId) {
        DeptInfo deptInfo = deptInfoMapper.selectDeptInfoById(deptId);
        if (deptInfo != null && deptInfo.getParentId() > 0) {
            DeptInfo parent = deptInfoMapper.selectDeptInfoById(deptInfo.getParentId());
            deptInfo.setParent(parent);
        }
        return deptInfo;
    }

    /**
     * 查询部门列表
     *
     * @param deptInfo 部门
     * @return 部门
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<DeptInfo> selectDeptInfoList(DeptInfo deptInfo) {
        return deptInfoMapper.selectDeptInfoList(deptInfo);
    }

    /**
     * 新增部门
     *
     * @param deptInfo 部门
     * @return 结果
     */
    @Override
    public int insertDeptInfo(DeptInfo deptInfo) {

        DeptInfo parentDept = deptInfoMapper.selectDeptInfoById(deptInfo.getParentId());
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(parentDept.getStatus())) {
            throw new GlobalException("部门停用，不允许新增");
        }
        deptInfo.setCreateBy(ShiroUtils.getLoginName());
        deptInfo.setCreateTime(DateUtils.getNowDate());
        deptInfo.setAncestors(parentDept.getAncestors() + "," + deptInfo.getParentId());
        return deptInfoMapper.insertDeptInfo(deptInfo);
    }

    /**
     * 修改部门
     *
     * @param deptInfo 部门
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDeptInfo(DeptInfo deptInfo) {
        DeptInfo newParentDept = deptInfoMapper.selectDeptInfoById(deptInfo.getParentId());
        DeptInfo oldDept = selectDeptInfoById(deptInfo.getDeptId());
        if (newParentDept != null && oldDept != null) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            deptInfo.setAncestors(newAncestors);
            updateDeptChildren(deptInfo.getDeptId(), newAncestors, oldAncestors);
        }
        deptInfo.setUpdateBy(ShiroUtils.getLoginName());
        deptInfo.setUpdateTime(DateUtils.getNowDate());
        int result = deptInfoMapper.updateDeptInfo(deptInfo);
        if (UserConstants.DEPT_NORMAL.equals(deptInfo.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(deptInfo);
        }
        return result;
    }

    /**
     * 删除部门对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDeptInfoByIds(String ids) {
        return deptInfoMapper.deleteDeptInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除部门信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptInfoById(Long deptId) {
        return deptInfoMapper.deleteDeptInfoById(deptId);
    }

    @Override
    public List<Ztree> roleDeptTreeData(RoleInfo roleInfo) {
        Long roleId = roleInfo.getRoleId();
        List<DeptInfo> deptList = selectDeptInfoList(new DeptInfo());
        if (roleId != null) {
            List<String> roleDeptList = deptInfoMapper.selectRoleDeptTree(roleId);
            return initZtree(deptList, roleDeptList);
        }
        return initZtree(deptList);
    }

    @Override
    public String checkDeptNameUnique(DeptInfo deptInfo) {
        long deptId = deptInfo.getDeptId() == null ? -1L : deptInfo.getDeptId();
        DeptInfo info = deptInfoMapper.checkDeptNameUnique(deptInfo.getDeptName(), deptInfo.getParentId());
        if (info != null && info.getDeptId() != deptId) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptInfoTree(DeptInfo deptInfo) {
        List<DeptInfo> deptList = deptInfoMapper.selectDeptInfoList(deptInfo);
        return initZtree(deptList);
    }

    @Override
    public int selectParentCount(Long parentId) {
        DeptInfo deptInfo = new DeptInfo();
        deptInfo.setParentId(parentId);
        return deptInfoMapper.selectDeptCount(deptInfo);
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptInfoMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<DeptInfo> deptList) {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList     部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<DeptInfo> deptList, List<String> roleDeptList) {
        List<Ztree> zTrees = new ArrayList<>();
        boolean isCheck = !CollectionUtils.isEmpty(roleDeptList);
        for (DeptInfo dept : deptList) {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setPId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck) {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                zTrees.add(ztree);
            }
        }
        return zTrees;
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<DeptInfo> children = deptInfoMapper.selectChildrenDeptById(deptId);
        for (DeptInfo child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptInfoMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param deptInfo 当前部门
     */
    private void updateParentDeptStatus(DeptInfo deptInfo) {
        deptInfoMapper.updateAncestorsStatus(deptInfo);
    }

}
