package com.guigui.perona.controller;


import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.service.IArtTagService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@Slf4j
@RestController
@RequestMapping("/api/tag")
@Api(value = "TagController", tags = {"标签管理接口"})
public class ArtTagController extends BaseController {
    @Autowired
    private IArtTagService tagService;

    @GetMapping("/findAll")
    public Return newList() {
        return new Return<>(tagService.findAll());
    }

    @GetMapping("/list")
    public Return findByPage(ArtTag tag, QueryPage queryPage) {
        return new Return<>(super.getData(tagService.list(tag, queryPage)));
    }

    @GetMapping("/{id}")
    public Return findById(@PathVariable Long id) {
        return new Return<>(tagService.getById(id));
    }

    @PostMapping
    @Log("新增标签")
    public Return save(@RequestBody ArtTag tag) {
        try {
            tagService.add(tag);
            return new Return();
        } catch (Exception e) {
            log.error("新增标签出现异常！tag: {}", tag.getName(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新标签")
    public Return update(@RequestBody ArtTag tag) {
        try {
            tagService.update(tag);
            return new Return();
        } catch (Exception e) {
            log.error("更新标签出现异常！tag: {}", tag.getName(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除标签")
    public Return delete(@PathVariable Long id) {
        try {
            tagService.delete(id);
            return new Return();
        } catch (Exception e) {
            log.error("删除标签出现异常！tagId: {}", id, e);
            throw new GlobalException(e.getMessage());
        }
    }
}
