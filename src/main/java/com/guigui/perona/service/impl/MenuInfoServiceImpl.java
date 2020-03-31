package com.guigui.perona.service.impl;

import java.util.*;

import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.common.utils.StringUtils;
import com.guigui.perona.common.utils.TreeUtils;
import com.guigui.perona.entity.RoleInfo;
import com.guigui.perona.entity.UserInfo;
import com.guigui.perona.manage.web.domain.Ztree;
import com.guigui.perona.mapper.RoleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.MenuInfoMapper;
import com.guigui.perona.entity.MenuInfo;
import com.guigui.perona.service.IMenuInfoService;
import com.guigui.perona.common.utils.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 菜单权限Service业务层处理
 *
 * @author guigui
 * @date 2020-03-10
 */
@Service
public class MenuInfoServiceImpl implements IMenuInfoService {

    @Autowired
    private MenuInfoMapper menuInfoMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public List<MenuInfo> selectMenuInfosByUser(UserInfo userInfo) {
        List<MenuInfo> menus;
        // 管理员显示所有菜单信息
        if (userInfo.isSuperAdmin()) {
            menus = menuInfoMapper.selectMenuNormalAll();
        } else {
            menus = menuInfoMapper.selectMenusByUserId(userInfo.getId());
        }
        return TreeUtils.getChildPerms(menus, 0);
    }

    /**
     * 查询菜单权限
     *
     * @param menuId 菜单权限ID
     * @return 菜单权限
     */
    @Override
    public MenuInfo selectMenuInfoById(Long menuId) {
        return menuInfoMapper.selectMenuInfoById(menuId);
    }

    /**
     * 查询菜单权限列表
     *
     * @param menuInfo 菜单权限
     * @return 菜单权限
     */
    @Override
    public List<MenuInfo> selectMenuInfoList(MenuInfo menuInfo) {
        List<MenuInfo> menuList;
        UserInfo userInfo = ShiroUtils.getUserInfo();
        if (userInfo.isSuperAdmin()) {
            menuList = menuInfoMapper.selectMenuInfoList(menuInfo);
        } else {
            menuList = menuInfoMapper.selectMenuAllByUserId(userInfo.getId());
        }
        return menuList;
    }

    /**
     * 查询全部菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<MenuInfo> selectMenuInfoAll() {
        List<MenuInfo> menuList;
        UserInfo userInfo = ShiroUtils.getUserInfo();
        if (userInfo.isSuperAdmin()) {
            menuList = menuInfoMapper.selectAllMenuInfo();
        } else {
            menuList = menuInfoMapper.selectMenuAllByUserId(userInfo.getId());
        }
        return menuList;
    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuInfoMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Ztree> generateMenuTree() {
        List<MenuInfo> menuList = selectMenuInfoAll();
        return initZtree(menuList);
    }

    @Override
    public List<Ztree> roleMenuTreeData(RoleInfo role) {
        Long roleId = role.getRoleId();
        List<MenuInfo> menuList = selectMenuInfoAll();
        if (roleId != null) {
            List<String> roleMenuList = menuInfoMapper.selectRoleMenuTree(roleId);
            return initZtree(menuList, roleMenuList, true);
        }
        return initZtree(menuList, null, true);
    }

    @Override
    public String checkMenuNameUnique(MenuInfo menuInfo) {
        long menuId = menuInfo.getMenuId() == null ? -1L : menuInfo.getMenuId();
        MenuInfo uniqueMenu = menuInfoMapper.checkMenuNameUnique(menuInfo.getMenuName(), menuInfo.getParentId());
        // 修改情况需要判断是否与当前选在menuID一致
        if (uniqueMenu != null && uniqueMenu.getMenuId() != menuId) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }

    @Override
    public int selectCountMenuByParentId(Long menuId) {
        return menuInfoMapper.selectCountMenuByParentId(menuId);
    }

    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return roleInfoMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 新增菜单权限
     *
     * @param menuInfo 菜单权限
     * @return 结果
     */
    @Override
    public int insertMenuInfo(MenuInfo menuInfo) {
        menuInfo.setCreateTime(DateUtils.getNowDate());
        menuInfo.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return menuInfoMapper.insertMenuInfo(menuInfo);
    }

    /**
     * 修改菜单权限
     *
     * @param menuInfo 菜单权限
     * @return 结果
     */
    @Override
    public int updateMenuInfo(MenuInfo menuInfo) {
        menuInfo.setUpdateTime(DateUtils.getNowDate());
        menuInfo.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return menuInfoMapper.updateMenuInfo(menuInfo);
    }

    /**
     * 删除菜单权限对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMenuInfoByIds(String ids) {
        return menuInfoMapper.deleteMenuInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除菜单权限信息
     *
     * @param menuId 菜单权限ID
     * @return 结果
     */
    @Override
    public int deleteMenuInfoById(Long menuId) {
        ShiroUtils.clearCachedAuthorizationInfo();
        return menuInfoMapper.deleteMenuInfoById(menuId);
    }

    /**
     * 菜单列表转菜单树
     *
     * @param menuInfoList 菜单列表
     * @return 菜单树
     */
    private List<Ztree> initZtree(List<MenuInfo> menuInfoList) {
        return initZtree(menuInfoList, null, false);
    }

    /**
     * 菜单列表转菜单树
     *
     * @param menuInfoList 菜单列表
     * @param roleMenuList 角色已存在的菜单列表
     * @param showPerms    是否需要显示权限标识
     * @return 菜单树
     */
    private List<Ztree> initZtree(List<MenuInfo> menuInfoList, List<String> roleMenuList, boolean showPerms) {
        List<Ztree> zTrees = new ArrayList<>();
        boolean isCheck = !CollectionUtils.isEmpty(roleMenuList);
        for (MenuInfo menu : menuInfoList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setPId(menu.getParentId());
            ztree.setName(transMenuName(menu, showPerms));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            zTrees.add(ztree);
        }
        return zTrees;
    }

    // 转换菜单名称
    private String transMenuName(MenuInfo menu, boolean showPerms) {
        StringBuilder sb = new StringBuilder();
        sb.append(menu.getMenuName());
        if (showPerms) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;").append(menu.getPerms()).append("</font>");
        }
        return sb.toString();
    }
}
