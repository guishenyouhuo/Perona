package com.guigui.perona.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guigui.perona.common.constants.SiteConstants;
import com.guigui.perona.common.dto.CommentTree;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.CommentMapper;
import com.guigui.perona.entity.Comment;
import com.guigui.perona.service.ICommentService;
import com.guigui.perona.common.utils.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 评论Service业务层处理
 * 
 * @author guigui
 * @date 2020-03-26
 */
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 查询评论
     * 
     * @param id 评论ID
     * @return 评论
     */
    @Override
    public Comment selectCommentById(Long id) {
        return commentMapper.selectCommentById(id);
    }

    /**
     * 查询评论列表
     * 
     * @param comment 评论
     * @return 评论
     */
    @Override
    public List<Comment> selectCommentList(Comment comment) {
        return commentMapper.selectCommentList(comment);
    }

    /**
     * 新增评论
     * 
     * @param comment 评论
     * @return 结果
     */
    @Override
    public int insertComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    /**
     * 修改评论
     * 
     * @param comment 评论
     * @return 结果
     */
    @Override
    public int updateComment(Comment comment) {
        return commentMapper.updateComment(comment);
    }

    /**
     * 删除评论对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCommentByIds(String ids) {
        return commentMapper.deleteCommentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除评论信息
     * 
     * @param id 评论ID
     * @return 结果
     */
    @Override
    public int deleteCommentById(Long id) {
        return commentMapper.deleteCommentById(id);
    }

    @Override
    public List<Comment> selectRecentComments(String state, int recentNum) {
        return commentMapper.selectRecentComments(state, recentNum);
    }

    @Override
    public Map<String, Object> getCommentsBySort(int page, int sort, String articleId) {

        Map<String, Object> map = new HashMap<>();
        Comment commentReq = new Comment();
        commentReq.setpId(0L);
        if (StringUtils.isNotBlank(articleId)) {
            commentReq.setArticleId(Long.valueOf(articleId));
        }
        commentReq.setSort((long)sort);
        PageHelper.startPage(page, SiteConstants.COMMENT_PAGE_LIMIT, "id desc");
        List<Comment> rootList = commentMapper.selectCommentList(commentReq);
        PageInfo<Comment> pageInfo = new PageInfo<>(rootList);
        map.put("count", pageInfo.getSize());
        map.put("total", pageInfo.getTotal());
        map.put("current", pageInfo.getPageNum());
        map.put("pages", pageInfo.getPages());
        if (CollectionUtils.isEmpty(rootList)) {
            map.put("rows", new ArrayList<>());
            return map;
        }
        // 转换成评论树
        Map<Long, CommentTree<Comment>> commentTreeMap = convertCommentTree(rootList);
        // 转换子节点
        transferChildren(rootList, commentTreeMap);
        List<CommentTree<Comment>> treeList = new ArrayList<>(rootList.size());
        rootList.forEach(comment -> {
            treeList.add(commentTreeMap.get(comment.getId()));
        });
        map.put("rows", treeList);
        return map;
    }

    private void transferChildren(List<Comment> parentList, Map<Long, CommentTree<Comment>> commentTreeMap) {
        List<Long> parentIds = new ArrayList<>(parentList.size());
        parentList.forEach(c -> {
            parentIds.add(c.getId());
        });
        // 批量查询子节点
        List<Comment> nodeList = commentMapper.selectCommentsByPidList(parentIds);
        // 没有子节点，退出
        if (CollectionUtils.isEmpty(nodeList)) {
            return;
        }
        Map<Long, CommentTree<Comment>> nodeTreeMap = convertCommentTree(nodeList);

        // 设置子节点到对应父节点(保证顺序)
        nodeList.forEach(node -> {
            CommentTree<Comment> treeNode = nodeTreeMap.get(node.getId());
            CommentTree<Comment> parentNode = commentTreeMap.get(treeNode.getPId());
            if (parentNode != null) {
                parentNode.getChildren().add(treeNode);
            }
        });
        // 递归处理子节点
        transferChildren(nodeList, nodeTreeMap);
    }

    private Map<Long, CommentTree<Comment>> convertCommentTree(List<Comment> commentList) {
        Map<Long, CommentTree<Comment>> resultMap = new HashMap<>(commentList.size());
        if (CollectionUtils.isEmpty(commentList)) {
            return resultMap;
        }
        commentList.forEach(c -> {
            CommentTree<Comment> tree = new CommentTree<>();
            tree.setId(c.getId());
            tree.setPId(c.getPId());
            tree.setAId(c.getArticleId());
            tree.setContent(c.getContent());
            tree.setName(c.getName());
            tree.setTarget(c.getCName());
            tree.setUrl(c.getUrl());
            tree.setDevice(c.getDevice());
            tree.setTime(DateUtils.parseDateToStr(c.getTime()));
            tree.setSort(c.getSort().intValue());
            resultMap.put(tree.getId(), tree);
        });
        return resultMap;
    }

}
