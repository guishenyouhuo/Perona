package com.guigui.perona.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.dto.ArchivesWithArticle;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.*;
import com.guigui.perona.mapper.ArticleMapper;
import com.guigui.perona.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IArtTagService artTagService;

    @Autowired
    private IArticleCategoryService articleCategoryService;

    @Autowired
    private IArticleTagService articleTagService;

    @Override
    public List<Article> findAll() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getId);
        queryWrapper.eq(Article::getState, CommonConstants.DEFAULT_RELEASE_STATUS);
        IPage<Article> page = new Page<>(0, 8);
        return articleMapper.selectPage(page, queryWrapper).getRecords();
    }

    @Override
    public List<ArchivesWithArticle> findArchives() {
        List<ArchivesWithArticle> archivesWithArticleList = new ArrayList<ArchivesWithArticle>();
        try {
            List<String> dates = articleMapper.findArchivesDates();
            dates.forEach(date -> {
                List<Article> articleList = articleMapper.findArchivesByDate(date);
                ArchivesWithArticle archivesWithArticle = new ArchivesWithArticle(date, articleList);
                archivesWithArticleList.add(archivesWithArticle);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return archivesWithArticleList;
    }

    @Override
    public Article findById(Long id) {
        Article sysArticle = articleMapper.selectById(id);
        List<Article> sysArticleList = new ArrayList<>();
        sysArticleList.add(sysArticle);
        findInit(sysArticleList);
        return sysArticle;
    }

    @Override
    public IPage<Article> list(Article article, QueryPage queryPage) {
        IPage<Article> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getId);
        queryWrapper.like(StringUtils.isNotBlank(article.getTitle()), Article::getTitle, article.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(article.getAuthor()), Article::getAuthor, article.getAuthor());
        IPage<Article> selectPage = articleMapper.selectPage(page, queryWrapper);
        findInit(selectPage.getRecords());
        return selectPage;
    }

    @Override
    @Transactional
    public void add(Article article) {
        if (article.getState() == null) {
            article.setState(CommonConstants.DEFAULT_DRAFT_STATUS);
        }
        if (article.getPublishTime() == null && article.getState().equals("1")) {
            article.setPublishTime(LocalDateTime.now());
        }
        // 随机生成封面图
        article.setCover(String.valueOf(new Random().nextInt(CommonConstants.COVER_RAND_COUNT) + 1));
        article.setAuthor(((UserInfo) SecurityUtils.getSubject().getPrincipal()).getUsername());
        article.setEditTime(LocalDateTime.now());
        article.setCreateTime(LocalDateTime.now());
        articleMapper.insert(article);
        article.setId(article.getId());
        this.updateArticleCategoryTags(article);
    }

    @Override
    @Transactional
    public void update(Article article) {
        if (article.getPublishTime() == null && article.getState().equals("1")) {
            article.setPublishTime(LocalDateTime.now());
        }
        articleMapper.updateById(article);
        updateArticleCategoryTags(article);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id != null && id != 0) {
            articleMapper.deleteById(id);
            //删除文章-分类表的关联
            articleCategoryService.deleteByArticleId(id);
            //删除文章-标签表的关联
            articleTagService.deleteByArticleId(id);
        }
    }

    @Override
    public List<Article> findByCategory(String category) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getCategory, category);
        return articleMapper.selectList(queryWrapper);
    }

    /**
     * 更新文章-分类-标签，三表间的关联
     *
     * @param article
     */
    private void updateArticleCategoryTags(Article article) {
        if (article.getId() != 0) {
            if (article.getCategory() != null) {
                articleCategoryService.deleteByArticleId(article.getId());
                Category sysCategory = categoryService.getById(article.getCategory());
                if (sysCategory != null) {
                    articleCategoryService.add(new ArticleCategory(article.getId(), sysCategory.getId()));
                }
            }
            if (article.getArtTags() != null && article.getArtTags().size() > 0) {
                articleTagService.deleteByArticleId(article.getId());
                article.getArtTags().forEach(tag -> {
                    articleTagService.add(new ArticleTag(article.getId(), tag.getId()));
                });
            }
        }
    }

    /**
     * 封装文章分类、标签数据
     *
     * @param list
     */
    private void findInit(List<Article> list) {
        if (!list.isEmpty()) {
            list.forEach(article -> {
                List<Category> sysCategoryList = categoryService.findByArticleId(article.getId());
                if (sysCategoryList.size() > 0) {
                    article.setCategoryName(sysCategoryList.get(0).getName());
                }
                List<ArtTag> tagList = artTagService.findByArticleId(article.getId());
                List<String> stringList = new ArrayList<>();
                tagList.forEach(tags -> {
                    stringList.add(tags.getName());
                });
                article.setArtTags(tagList);
                article.setTagNames(stringList);
            });
        }
    }
}
