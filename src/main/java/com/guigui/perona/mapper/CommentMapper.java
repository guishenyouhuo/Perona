package com.guigui.perona.mapper;

import com.guigui.perona.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论Mapper接口
 *
 * @author guigui
 * @date 2020-03-26
 */
public interface CommentMapper {
    /**
     * 根据主键查询评论
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
     * 删除评论
     *
     * @param id 评论ID
     * @return 结果
     */
    int deleteCommentById(Long id);

    /**
     * 批量删除评论
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCommentByIds(String[] ids);

    /**
     * 查询最近评论
     *
     * @param state     状态
     * @param recentNum 最近数量
     * @return 评论列表
     */
    List<Comment> selectRecentComments(@Param("state") String state, @Param("recentNum") int recentNum);

    /**
     * 根据父节点id查找评论
     *
     * @param pId 父节点id
     * @return 评论列表
     */
    List<Comment> selectCommentsByPid(@Param("pId") Long pId);

    /**
     * 根据父节点id列表批量查询评论列表
     *
     * @param pIdList 父节点id列表
     * @return 评论列表
     */
    List<Comment> selectCommentsByPidList(@Param("pIdList") List<Long> pIdList);
}
