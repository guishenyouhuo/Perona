package com.guigui.perona.mapper;

import com.guigui.perona.entity.ArtTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
public interface ArtTagMapper extends BaseMapper<ArtTag> {
    List<ArtTag> findByArticleId(long id);
}
