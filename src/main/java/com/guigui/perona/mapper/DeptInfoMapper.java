package com.guigui.perona.mapper;

import com.guigui.perona.entity.DeptInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门Mapper接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface DeptInfoMapper {
    /**
     * 根据主键查询部门
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
     * 删除部门
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptInfoById(Long deptId);

    /**
     * 批量删除部门
     *
     * @param deptIds 需要删除的数据ID
     * @return 结果
     */
    int deleteDeptInfoByIds(String[] deptIds);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    List<String> selectRoleDeptTree(Long roleId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    DeptInfo checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 修改所在部门的父级部门状态
     *
     * @param deptInfo 部门
     */
    void updateAncestorsStatus(DeptInfo deptInfo);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<DeptInfo> selectChildrenDeptById(Long deptId);

    /**
     * 修改子元素关系
     *
     * @param deptInfos 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("deptInfos") List<DeptInfo> deptInfos);

    /**
     * 查询部门数
     *
     * @param dept 部门信息（参数）
     * @return 结果
     */
    int selectDeptCount(DeptInfo dept);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int checkDeptExistUser(Long deptId);
}
