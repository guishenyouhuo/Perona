package com.guigui.perona.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 友链表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
public interface IFriendLinkService extends IService<FriendLink> {

    IPage<FriendLink> list(FriendLink link, QueryPage queryPage);

    void add(FriendLink link);

    void update(FriendLink link);

    void delete(Long id);
}
