package com.guigui.perona.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.entity.ArtTag;
import com.guigui.perona.mapper.ArtTagMapper;
import com.guigui.perona.service.IArtTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigui.perona.service.IArticleTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@Service
public class ArtTagServiceImpl extends ServiceImpl<ArtTagMapper, ArtTag> implements IArtTagService {

    @Autowired
    private ArtTagMapper artTagMapper;

    @Autowired
    private IArticleTagService articleTagService;

    @Override
    public List<ArtTag> findAll() {
        return artTagMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public IPage<ArtTag> list(ArtTag tag, QueryPage queryPage) {
        IPage<ArtTag> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<ArtTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(tag.getName()), ArtTag::getName, tag.getName());
        queryWrapper.orderByDesc(ArtTag::getId);
        return artTagMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void add(ArtTag tag) {
        if (!exists(tag)) {
            artTagMapper.insert(tag);
        }
    }

    private boolean exists(ArtTag tag) {
        LambdaQueryWrapper<ArtTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArtTag::getName, tag.getName());
        return artTagMapper.selectList(queryWrapper).size() > 0;
    }

    @Override
    @Transactional
    public void update(ArtTag tag) {
        artTagMapper.updateById(tag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        artTagMapper.deleteById(id);
        //删除该标签与文章有关联的关联信息
        articleTagService.deleteByTagsId(id);
    }

    @Override
    public List<ArtTag> findByArticleId(Long id) {
        return artTagMapper.findByArticleId(id);
    }

}
