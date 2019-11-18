package com.guigui.perona.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.constants.SiteConstants;
import com.guigui.perona.common.dto.CommentTree;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.CommentTreeUtil;
import com.guigui.perona.common.utils.DateUtil;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.Comment;
import com.guigui.perona.mapper.CommentMapper;
import com.guigui.perona.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findAll() {
        return commentMapper.findAll(CommonConstants.DEFAULT_RELEASE_STATUS, new QueryPage(0, SiteConstants.COMMENT_PAGE_LIMIT));
    }

    @Override
    public Map<String, Object> findCommentsList(QueryPage queryPage, String articleId, int sort) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(articleId), Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getSort, sort);
        queryWrapper.orderByDesc(Comment::getId);
        //这里暂时先采用：先查询所有、再分页的方式
        List<Comment> list = commentMapper.selectList(queryWrapper);
        List<CommentTree<Comment>> trees = new ArrayList<>();
        list.forEach(c -> {
            CommentTree<Comment> tree = new CommentTree<>();
            tree.setId(c.getId());
            tree.setPId(c.getPId());
            tree.setAId(c.getArticleId());
            tree.setContent(c.getContent());
            tree.setName(c.getName());
            tree.setTarget(c.getCName());
            tree.setUrl(c.getUrl());
            tree.setDevice(c.getDevice());
            tree.setTime(DateUtil.dateFormat(c.getTime()));
            tree.setSort(c.getSort().intValue());
            trees.add(tree);
        });
        Map<String, Object> map = new HashMap<>();
        try {
            List<CommentTree<Comment>> treeList = CommentTreeUtil.build(trees);

            if (treeList == null) {
                treeList = new ArrayList<>();
            }

            if (treeList.size() == 0) {
                map.put("rows", new ArrayList<>());
            } else {
                int start = (queryPage.getPage() - 1) * queryPage.getLimit();
                int end = queryPage.getPage() * queryPage.getLimit();
                if (queryPage.getPage() * queryPage.getLimit() >= treeList.size()) {
                    end = treeList.size();
                }
                map.put("rows", treeList.subList(start, end));
            }
            map.put("count", list.size());
            map.put("total", treeList.size());
            map.put("current", queryPage.getPage());
            map.put("pages", (int) Math.ceil((double) treeList.size() / (double) queryPage.getLimit()));
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        return map;
    }

    @Override
    public IPage<Comment> list(Comment comment, QueryPage queryPage) {
        IPage<Comment> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Comment::getId);
        queryWrapper.like(StringUtils.isNotBlank(comment.getName()), Comment::getName, comment.getName());
        queryWrapper.like(StringUtils.isNotBlank(comment.getUrl()), Comment::getUrl, comment.getUrl());
        return commentMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int findCountByArticle(Long articleId) {
        IPage<Comment> page = new Page<>(0, 8);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        return commentMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional
    public void add(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        commentMapper.updateById(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }

}
