package com.guigui.perona.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 查询所有评论
     *
     * @return
     */
    List<Comment> findAll();

    /**
     * 分页查询并过滤留言数据
     *
     * @param articleId 当前访问的文章ID
     * @param sort      分类，规定：sort=0表示文章详情页的评论信息；sort=1表示友链页的评论信息；sort=2表示关于我页的评论信息
     * @return
     */
    Map<String, Object> findCommentsList(QueryPage queryPage, String articleId, int sort);

    /**
     * 分页查询
     *
     * @param comment
     * @param queryPage
     * @return
     */
    IPage<Comment> list(Comment comment, QueryPage queryPage);

    /**
     * 查询指定文章下的评论量
     *
     * @param articleId
     * @return
     */
    int findCountByArticle(Long articleId);

    /**
     * 新增
     *
     * @param comment
     */
    void add(Comment comment);

    /**
     * 更新
     *
     * @param comment
     */
    void update(Comment comment);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);
}
