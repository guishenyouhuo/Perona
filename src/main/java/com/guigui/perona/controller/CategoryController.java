package com.guigui.perona.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.Article;
import com.guigui.perona.entity.Category;
import com.guigui.perona.service.IArticleService;
import com.guigui.perona.service.ICategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/api/category")
@Api(value = "CategoryController", tags = {"分类管理接口"})
public class CategoryController extends BaseController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IArticleService articleService;

    @GetMapping("/findAll")
    public Return<List<Category>> findAll() {
        return new Return<>(categoryService.list(new QueryWrapper<>()));
    }

    /**
     * 查询所有的分类（包含对应的文章数量），结构：
     * [{分类名称，数量},{},....]
     *
     * @return
     */
    @GetMapping("/findArticleCountForCategory")
    public Return<Map<String, Object>> findArticleCountForCategory() {
        Map<String, Object> map = new HashMap<>();
        List<Category> sysCategoryList = categoryService.list(new QueryWrapper<>());
        for (Category sysCategory : sysCategoryList) {
            List<Article> sysArticleList = articleService.findByCategory(sysCategory.getName());
            map.put(sysCategory.getName(), sysArticleList.size());
        }
        return new Return<>(map);
    }

    @GetMapping("/list")
    public Return<Map<String, Object>> list(Category sysCategory, QueryPage queryPage) {
        return new Return<>(super.getData(categoryService.list(sysCategory, queryPage)));
    }

    @GetMapping("/{id}")
    public Return<Category> findById(@PathVariable("id") Long id) {
        return new Return<>(categoryService.getById(id));
    }

    @PostMapping
    @Log("新增分类")
    public Return save(@RequestBody Category category) {
        try {
            categoryService.add(category);
            return new Return();
        } catch (Exception e) {
            log.error("新增分类出现异常！category: {}", category.getName(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新分类")
    public Return update(@RequestBody Category category) {
        try {
            categoryService.update(category);
            return new Return();
        } catch (Exception e) {
            log.error("更新分类出现异常！category: {}", category.getName(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除分类")
    public Return delete(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return new Return();
        } catch (Exception e) {
            log.error("删除分类出现异常！categoryId: {}", id, e);
            throw new GlobalException(e.getMessage());
        }
    }
}
