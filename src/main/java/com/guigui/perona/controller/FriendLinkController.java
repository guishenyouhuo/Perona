package com.guigui.perona.controller;


import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.FriendLink;
import com.guigui.perona.service.IFriendLinkService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

/**
 * <p>
 * 友链表 前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@Slf4j
@RestController
@RequestMapping("/api/link")
@Api(value = "LinkController", tags = {"友链管理接口"})
public class FriendLinkController extends BaseController {
    @Autowired
    private IFriendLinkService linkService;

    @GetMapping("/list")
    public Return list(FriendLink link, QueryPage queryPage) {
        return new Return<>(super.getData(linkService.list(link, queryPage)));
    }

    @GetMapping("/{id}")
    public Return findById(@PathVariable Long id) {
        return new Return<>(linkService.getById(id));
    }

    @PostMapping
    @Log("新增友链")
    public Return save(@RequestBody FriendLink link) {
        try {
            linkService.add(link);
            return new Return();
        } catch (Exception e) {
            log.error("新增友链出现异常！link: {}", link.getName(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新友链")
    public Return update(@RequestBody FriendLink link) {
        try {
            linkService.update(link);
            return new Return();
        } catch (Exception e) {
            log.error("更新友链出现异常！link: {}", link.getName(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除友链")
    public Return delete(@PathVariable Long id) {
        try {
            linkService.delete(id);
            return new Return();
        } catch (Exception e) {
            log.error("删除友链出现异常！linkId: {}", id, e);
            throw new GlobalException(e.getMessage());
        }
    }
}
