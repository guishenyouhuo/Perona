package com.guigui.perona.mapper;

import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> findAll(@Param("state") String state, @Param("queryPage") QueryPage queryPage);
}
