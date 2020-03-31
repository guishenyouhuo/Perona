package com.guigui.perona.service;

import com.guigui.perona.entity.MenuInfo;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.manage.web.domain.Ztree;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限Service接口
 *
 * @author guigui
 * @date 2020-03-10
 */
public interface IMenuInfoService {

    /**
     * 根据用户ID查询菜单
     *
     * @param userInfo 用户信息
     * @return 菜单列表
     */
    List<MenuInfo> selectMenuInfosByUser(UserInfo userInfo);

    /**
     * 查询菜单权限
     *
     * @param menuId 菜单权限ID
     * @return 菜单权限
     */
    MenuInfo selectMenuInfoById(Long menuId);

    /**
     * 查询菜单权限列表
     *
     * @param menuInfo 菜单权限
     * @return 菜单权限集合
     */
    List<MenuInfo> selectMenuInfoList(MenuInfo menuInfo);

    /**
     * 查询全部菜单集合
     *
     * @return 菜单集合
     */
    List<MenuInfo> selectMenuInfoAll();

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 生成菜单树
     *
     * @return 菜单树
     */
    List<Ztree> generateMenuTree();

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    List<Ztree> roleMenuTreeData(RoleInfo role);

    /**
     * 校验菜单名称是否唯一
     * @param menuInfo 菜单信息
     * @return 是否唯一
     */
    String checkMenuNameUnique(MenuInfo menuInfo);

    /**
     * 查询是否存在子菜单
     * @param menuId 菜单id
     * @return 子菜单数
     */
    int selectCountMenuByParentId(Long menuId);

    /**
     * 查询菜单关联角色数量
     *
     * @param menuId 菜单ID
     * @return 关联角色数
     */
    int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 新增菜单权限
     *
     * @param menuInfo 菜单权限
     * @return 结果
     */
    int insertMenuInfo(MenuInfo menuInfo);

    /**
     * 修改菜单权限
     *
     * @param menuInfo 菜单权限
     * @return 结果
     */
    int updateMenuInfo(MenuInfo menuInfo);

    /**
     * 批量删除菜单权限
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteMenuInfoByIds(String ids);

    /**
     * 删除菜单权限信息
     *
     * @param menuId 菜单权限ID
     * @return 结果
     */
    int deleteMenuInfoById(Long menuId);

}
