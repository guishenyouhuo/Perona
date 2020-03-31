package com.guigui.perona.service;

import com.guigui.perona.entity.FriendLink;
import java.util.List;

/**
 * 友链Service接口
 * 
 * @author guigui
 * @date 2020-03-28
 */
public interface IFriendLinkService {
    /**
     * 查询友链
     * 
     * @param id 友链ID
     * @return 友链
     */
    FriendLink selectFriendLinkById(Long id);

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
     * 批量删除友链
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFriendLinkByIds(String ids);

    /**
     * 删除友链信息
     * 
     * @param id 友链ID
     * @return 结果
     */
    int deleteFriendLinkById(Long id);

    /**
     * 校验友链名是否唯一
     *
     * @param friendLink 友链信息
     * @return 结果
     */
    String checkFriendLinkUnique(FriendLink friendLink);

}
