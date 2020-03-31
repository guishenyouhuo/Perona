package com.guigui.perona.mapper;

import com.guigui.perona.entity.FriendLink;

import java.util.List;

/**
 * 友链Mapper接口
 *
 * @author guigui
 * @date 2020-03-28
 */
public interface FriendLinkMapper {
    /**
     * 根据主键查询友链
     *
     * @param id 友链ID
     * @return 友链
     */
    FriendLink selectFriendLinkById(Long id);

    /**
     * 根据名称查询友链
     *
     * @param name 友链名称
     * @return 友链
     */
    FriendLink selectLinkByName(String name);

    /**
     * 查询友链列表
     *
     * @param friendLink 友链
     * @return 友链集合
     */
    List<FriendLink> selectFriendLinkList(FriendLink friendLink);

    /**
     * 新增友链
     *
     * @param friendLink 友链
     * @return 结果
     */
    int insertFriendLink(FriendLink friendLink);

    /**
     * 修改友链
     *
     * @param friendLink 友链
     * @return 结果
     */
    int updateFriendLink(FriendLink friendLink);

    /**
     * 删除友链
     *
     * @param id 友链ID
     * @return 结果
     */
    int deleteFriendLinkById(Long id);

    /**
     * 批量删除友链
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFriendLinkByIds(String[] ids);
}
