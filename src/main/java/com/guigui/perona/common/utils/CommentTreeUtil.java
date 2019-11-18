package com.guigui.perona.common.utils;

import com.guigui.perona.common.dto.CommentTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 评论树处理工具
 * @Author: guigui
 * @Date: 2019-10-30 16:59
 */
public class CommentTreeUtil {
    public static <T> List<CommentTree<T>> build(List<CommentTree<T>> nodes) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<CommentTree<T>> tree = new ArrayList<>();
        nodes.forEach(node -> {
            Long pId = node.getPId();
            if (pId == null || pId.equals(0L)) {
                //父节点
                tree.add(node);
                return;
            }
            for (CommentTree<T> c : nodes) {
                Long id = c.getId();
                if (id != null && id.equals(pId)) {
                    c.getChildren().add(node);
                    return;
                }
            }
        });
        return tree;
    }
}
