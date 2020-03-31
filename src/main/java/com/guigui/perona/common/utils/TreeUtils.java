package com.guigui.perona.common.utils;

import com.guigui.perona.entity.MenuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限数据处理
 *
 * @author guigui
 */
public class TreeUtils {
    /**
     * 根据节点的ID获取其所有子节点
     *
     * @param menuList 节点列表
     * @param menuId   传入的节点ID
     * @return String
     */
    public static List<MenuInfo> getChildPerms(List<MenuInfo> menuList, int menuId) {
        List<MenuInfo> returnList = new ArrayList<>();
        for (MenuInfo menu : menuList) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (menu.getParentId() == menuId) {
                recursionFn(menuList, menu);
                returnList.add(menu);
            }
        }
        return returnList;
    }

    /**
     * 递归处理列表
     *
     * @param menuList 节点列表
     * @param curMenu  当前节点
     */
    private static void recursionFn(List<MenuInfo> menuList, MenuInfo curMenu) {
        // 得到子节点列表
        List<MenuInfo> childList = getChildList(menuList, curMenu);
        curMenu.setChildren(childList);
        for (MenuInfo child : childList) {
            // 判断是否有子节点
            if (hasChild(menuList, child)) {
                // 递归处理子节点
                recursionFn(menuList, child);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private static List<MenuInfo> getChildList(List<MenuInfo> menuList, MenuInfo curMenu) {

        List<MenuInfo> childList = new ArrayList<>();
        for (MenuInfo menu : menuList) {
            if (menu.getParentId().longValue() == curMenu.getMenuId().longValue()) {
                childList.add(menu);
            }
        }
        return childList;
    }

    private List<MenuInfo> returnList = new ArrayList<>();

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list   分类表
     * @param typeId 传入的父节点ID
     * @param prefix 子节点前缀
     */
    public List<MenuInfo> getChildPerms(List<MenuInfo> list, int typeId, String prefix) {
        if (list == null) {
            return null;
        }
        for (MenuInfo node : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (node.getParentId() == typeId) {
                recursionFn(list, node, prefix);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*
             * if (node.getParentId()==0) { recursionFn(list, node); }
             */
        }
        return returnList;
    }

    private void recursionFn(List<MenuInfo> list, MenuInfo node, String p) {
        // 得到子节点列表
        List<MenuInfo> childList = getChildList(list, node);
        if (hasChild(list, node)) {
            // 判断是否有子节点
            returnList.add(node);
            for (MenuInfo n : childList) {
                n.setMenuName(p + n.getMenuName());
                recursionFn(list, n, p + p);
            }
        } else {
            returnList.add(node);
        }
    }

    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<MenuInfo> menuList, MenuInfo curMenu) {
        for (MenuInfo menu : menuList) {
            if (menu.getParentId().longValue() == curMenu.getMenuId().longValue()) {
                return true;
            }
        }
        return false;
    }
}
