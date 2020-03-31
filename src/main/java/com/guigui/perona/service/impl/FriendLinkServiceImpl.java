package com.guigui.perona.service.impl;

import java.util.List;

import com.guigui.perona.common.constants.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.FriendLinkMapper;
import com.guigui.perona.entity.FriendLink;
import com.guigui.perona.service.IFriendLinkService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 友链Service业务层处理
 * 
 * @author guigui
 * @date 2020-03-28
 */
@Service
public class FriendLinkServiceImpl implements IFriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    /**
     * 查询友链
     * 
     * @param id 友链ID
     * @return 友链
     */
    @Override
    public FriendLink selectFriendLinkById(Long id) {
        return friendLinkMapper.selectFriendLinkById(id);
    }

    /**
     * 查询友链列表
     * 
     * @param friendLink 友链
     * @return 友链
     */
    @Override
    public List<FriendLink> selectFriendLinkList(FriendLink friendLink) {
        return friendLinkMapper.selectFriendLinkList(friendLink);
    }

    /**
     * 新增友链
     * 
     * @param friendLink 友链
     * @return 结果
     */
    @Override
    public int insertFriendLink(FriendLink friendLink) {
        return friendLinkMapper.insertFriendLink(friendLink);
    }

    /**
     * 修改友链
     * 
     * @param friendLink 友链
     * @return 结果
     */
    @Override
    public int updateFriendLink(FriendLink friendLink) {
        return friendLinkMapper.updateFriendLink(friendLink);
    }

    /**
     * 删除友链对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFriendLinkByIds(String ids) {
        return friendLinkMapper.deleteFriendLinkByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除友链信息
     * 
     * @param id 友链ID
     * @return 结果
     */
    @Override
    public int deleteFriendLinkById(Long id) {
        return friendLinkMapper.deleteFriendLinkById(id);
    }

    @Override
    public String checkFriendLinkUnique(FriendLink friendLink) {
        long linkId = friendLink.getId() == null ? -1L : friendLink.getId();
        FriendLink info = friendLinkMapper.selectLinkByName(friendLink.getName());
        if (info != null && info.getId() != linkId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

}
