package com.guigui.perona.mapper;

import com.guigui.perona.entity.MenuInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限Mapper接口
 *
 * @author guigui
 * @date 2020-03-10
 */
public interface MenuInfoMapper {
    /**
     * 根据主键查询菜单权限
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
     * 查询系统正常显示菜单（不含按钮）
     *
     * @return 菜单列表
     */
    List<MenuInfo> selectMenuNormalAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuInfo> selectMenusByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuInfo> selectMenuAllByUserId(Long userId);

    /**
     * 获取全部菜单信息
     *
     * @return 菜单列表
     */
    List<MenuInfo> selectAllMenuInfo();

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @return 结果
     */
    MenuInfo checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询菜单树
     *
     * @param roleId 角色ID
     * @return 菜单权限列表
     */
    List<String> selectRoleMenuTree(Long roleId);

    /**
     * 查询菜单数量
     *
     * @param parentId 菜单父ID
     * @return 结果
     */
    int selectCountMenuByParentId(Long parentId);

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
     * 删除菜单权限
     *
     * @param menuId 菜单权限ID
     * @return 结果
     */
    int deleteMenuInfoById(Long menuId);

    /**
     * 批量删除菜单权限
     *
     * @param menuIds 需要删除的数据ID
     * @return 结果
     */
    int deleteMenuInfoByIds(String[] menuIds);
}
