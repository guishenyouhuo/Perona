package com.guigui.perona.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.dto.ArchivesWithArticle;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.entity.Article;
import com.guigui.perona.service.IArticleService;
import com.guigui.perona.service.IArticleTagService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/api/article")
@Api(value = "ArticleController", tags = {"文章管理接口"})
public class ArticleController extends BaseController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleTagService articleTagService;

    /**
     * 查询文章总数量
     *
     * @return
     */
    @GetMapping("/count")
    public Return<Integer> findAllCount() {
        return new Return<>(articleService.count(new QueryWrapper<>()));
    }

    /**
     * 分页查询
     *
     * @param queryPage
     * @param article
     * @return
     */
    @GetMapping("/list")
    public Return<Map<String, Object>> findByPage(Article article, QueryPage queryPage) {
        return new Return<>(super.getData(articleService.list(article, queryPage)));
    }

    @GetMapping("{id}")
    public Return<Article> findById(@PathVariable Long id) {
        return new Return<>(articleService.findById(id));
    }

    /**
     * 查询指定ArticleId的Tags数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/tags")
    public Return<List<String>> findTags(@PathVariable Long id) {
        List<String> list = new ArrayList<>();
        List<ArtTag> tagList = articleTagService.findByArticleId(id);
        for (ArtTag t : tagList) {
            list.add(t.getName());
        }
        return new Return<>(list);
    }

    /**
     * 查询所有的Archives
     *
     * @return
     */
    @GetMapping(value = "/archives")
    public Return<List<ArchivesWithArticle>> findArchives() {
        return new Return<>(articleService.findArchives());
    }

    @PostMapping
    @Log("新增文章")
    public Return save(@RequestBody Article article) {
        try {
            articleService.add(article);
            return new Return();
        } catch (Exception e) {
            log.error("保存文章出现异常！article:{}", article.getTitle(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新文章")
    public Return update(@RequestBody Article article) {
        try {
            articleService.update(article);
            return new Return();
        } catch (Exception e) {
            log.error("更新文章出现异常！article:{}", article.getTitle(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除文章")
    public Return delete(@PathVariable Long id) {
        try {
            articleService.delete(id);
            return new Return();
        } catch (Exception e) {
            log.error("删除文章出现异常！articleId:{}", id, e);
            throw new GlobalException(e.getMessage());
        }
    }
}
