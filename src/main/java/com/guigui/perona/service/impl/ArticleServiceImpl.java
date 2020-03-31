package com.guigui.perona.service.impl;

import java.util.*;

import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.dto.ArchivesWithArticle;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.entity.*;
import com.guigui.perona.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.ArticleMapper;
import com.guigui.perona.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 文章Service业务层处理
 *
 * @author guigui
 * @date 2020-03-25
 */
@Service
public class ArticleServiceImpl implements IArticleService {

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

    /**
     * 查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    @Override
    public Article selectArticleById(Long id) {
        Article article = articleMapper.selectArticleById(id);
        List<Article> articleList = Collections.singletonList(article);
        completeArticleList(articleList);
        return article;
    }

    /**
     * 查询文章列表
     *
     * @param articleReq 文章
     * @return 文章
     */
    @Override
    public List<Article> selectArticleList(Article articleReq) {
        List<Article> articleList = articleMapper.selectArticleList(articleReq);
        if (CollectionUtils.isEmpty(articleList)) {
            return articleList;
        }
        completeArticleList(articleList);
        return articleList;
    }

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 结果
     */
    @Override
    @Transactional
    public int insertArticle(Article article) {
        if (article.getState() == null) {
            article.setState(CommonConstants.DEFAULT_DRAFT_STATUS);
        }
        if (article.getPublishTime() == null && article.getState().equals("1")) {
            article.setPublishTime(DateUtils.getNowDate());
        }
        // 随机生成封面图
        article.setCover(String.valueOf(new Random().nextInt(CommonConstants.COVER_RAND_COUNT) + 1));
        article.setAuthor(ShiroUtils.getLoginName());
        article.setEditTime(DateUtils.getNowDate());
        article.setCreateTime(DateUtils.getNowDate());
        int ret = articleMapper.insertArticle(article);
        article.setId(article.getId());
        this.updateArticleCategoryTags(article);
        return ret;
    }

    /**
     * 修改文章
     *
     * @param article 文章
     * @return 结果
     */
    @Override
    @Transactional
    public int updateArticle(Article article) {
        if (article.getPublishTime() == null && article.getState().equals("1")) {
            article.setPublishTime(DateUtils.getNowDate());
        }
        int ret = articleMapper.updateArticle(article);
        this.updateArticleCategoryTags(article);
        return ret;
    }

    /**
     * 删除文章对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteArticleByIds(String ids) {
        // TODO
        return articleMapper.deleteArticleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文章信息
     *
     * @param id 文章ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteArticleById(Long id) {
        if (id == null || id == 0) {
            return 0;
        }
        int ret = articleMapper.deleteArticleById(id);
        //删除文章-分类表的关联
        articleCategoryService.deleteByArticleId(id);
        //删除文章-标签表的关联
        articleTagService.deleteByArticleId(id);
        return ret;
    }

    @Override
    public List<ArchivesWithArticle> findArchives() {
        List<ArchivesWithArticle> archivesWithArticleList = new ArrayList<>();
        try {
            List<String> dates = articleMapper.selectArchivesDates();
            // TODO  可优化
            dates.forEach(date -> {
                List<Article> articleList = articleMapper.selectArchivesByDate(date);
                ArchivesWithArticle archivesWithArticle = new ArchivesWithArticle(date, articleList);
                archivesWithArticleList.add(archivesWithArticle);
            });
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return archivesWithArticleList;
    }

    @Override
    public List<Article> selectRecentArticles(int recentNum) {
        return articleMapper.selectRecent(recentNum);
    }

    @Override
    public String checkArticleUnique(Article article) {
        long articleId = article.getId() == null ? -1L : article.getId();
        Article info = articleMapper.selectArticleTitle(article.getTitle());
        if (info != null && info.getId() != articleId) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 完善文章列表信息 分类、标签等数据
     *
     * @param articleList
     */
    private void completeArticleList(List<Article> articleList) {
        List<Long> articleIds = new ArrayList<>(articleList.size());
        articleList.forEach(article -> {
            articleIds.add(article.getId());
        });
        // 批量查询文章对应标签列表和分类
        Map<Long, List<ArtTag>> artTagsMap = artTagService.selectByArticleIds(articleIds);
        Map<Long, List<Category>> categoriesMap = categoryService.selectCategoryByArticleIds(articleIds);

        // 遍历文章列表填充标签和分类
        articleList.forEach(article -> {
            List<Category> categoryList = categoriesMap.get(article.getId());
            if (!CollectionUtils.isEmpty(categoryList)) {
                article.setCategoryName(categoryList.get(0).getName());
            }
            List<ArtTag> tagList = artTagsMap.get(article.getId());
            List<String> tagNameList = new ArrayList<>();
            String[] tagIds = null;
            if (!CollectionUtils.isEmpty(tagList)) {
                tagIds = new String[tagList.size()];
                for (int i = 0; i < tagList.size(); i++) {
                    ArtTag artTag = tagList.get(i);
                    tagNameList.add(artTag.getName());
                    tagIds[i] = artTag.getId().toString();
                }
            }
            article.setArtTags(tagList);
            article.setTagNames(tagNameList);
            article.setTagIds(tagIds);
        });
    }

    /**
     * 更新文章-分类-标签，三表间的关联
     *
     * @param article 文章信息
     */
    private void updateArticleCategoryTags(Article article) {
        if (article.getId() == null || article.getId() == 0) {
            return;
        }
        if (article.getCategory() != null) {
            articleCategoryService.deleteByArticleId(article.getId());
            Category category = categoryService.selectCategoryById(Long.parseLong(article.getCategory()));
            if (category != null) {
                ArticleCategory articleCategory = new ArticleCategory();
                articleCategory.setArticleId(article.getId());
                articleCategory.setCategoryId(category.getId());
                articleCategoryService.insertArticleCategory(articleCategory);
            }
        }
        String[] tagIds = article.getTagIds();
        if (tagIds != null && tagIds.length > 0) {
            articleTagService.deleteByArticleId(article.getId());
            List<ArticleTag> articleTags = new ArrayList<>();
            for (String tagId : tagIds) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.valueOf(tagId));
                articleTags.add(articleTag);
            }
            articleTagService.batchInsert(articleTags);
        }
    }

}
