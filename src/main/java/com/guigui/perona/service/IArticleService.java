package com.guigui.perona.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guigui.perona.common.dto.ArchivesWithArticle;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface IArticleService extends IService<Article> {

    List<Article> findAll();

    /**
     * 查询按照时间归档的整合数据，
     * 格式：[{"2019-01", [{article},{article}...]}, {"2018-02", [{article}, {article}...]}]
     *
     * @return
     */
    List<ArchivesWithArticle> findArchives();

    /**
     * 根据ID 查询
     *
     * @param id
     * @return
     */
    Article findById(Long id);

    /**
     * 分页查询
     *
     * @param article
     * @param queryPage
     * @return
     */
    IPage<Article> list(Article article, QueryPage queryPage);

    /**
     * 添加
     *
     * @param article
     */
    void add(Article article);

    /**
     * 更新
     *
     * @param article
     */
    void update(Article article);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 根据分类名称查询文章数据
     *
     * @param category
     * @return
     */
    List<Article> findByCategory(String category);
}
