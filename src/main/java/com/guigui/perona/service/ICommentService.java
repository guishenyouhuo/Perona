package com.guigui.perona.service;

import com.guigui.perona.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 评论Service接口
 *
 * @author guigui
 * @date 2020-03-26
 */
public interface ICommentService {
    /**
     * 查询评论
     *
     * @param id 评论ID
     * @return 评论
     */
    Comment selectCommentById(Long id);

    /**
     * 查询评论列表
     *
     * @param comment 评论
     * @return 评论集合
     */
    List<Comment> selectCommentList(Comment comment);

    /**
     * 新增评论
     *
     * @param comment 评论
     * @return 结果
     */
    int insertComment(Comment comment);

    /**
     * 修改评论
     *
     * @param comment 评论
     * @return 结果
     */
    int updateComment(Comment comment);

    /**
     * 批量删除评论
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCommentByIds(String ids);

    /**
     * 删除评论信息
     *
     * @param id 评论ID
     * @return 结果
     */
    int deleteCommentById(Long id);

    /**
     * 查询最近文章评论
     *
     * @param state     文章状态
     * @param recentNum 最近数
     * @return 评论列表
     */
    List<Comment> selectRecentComments(String state, int recentNum);

    /**
     * 根据页面查找对应评论树
     *
     * @param page      当前页
     * @param sort      页面标识
     * @param articleId 文章id
     * @return 结果
     */
    Map<String, Object> getCommentsBySort(int page, int sort, String articleId);

}
