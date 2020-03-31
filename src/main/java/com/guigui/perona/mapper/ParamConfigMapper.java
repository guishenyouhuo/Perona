package com.guigui.perona.mapper;

import com.guigui.perona.entity.ParamConfig;

import java.util.List;

/**
 * 参数配置Mapper接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface ParamConfigMapper {
    /**
     * 根据主键查询参数配置
     *
     * @param configId 参数配置ID
     * @return 参数配置
     */
    ParamConfig selectParamConfigById(Long configId);

    /**
     * 查询参数配置列表
     *
     * @param paramConfig 参数配置
     * @return 参数配置集合
     */
    List<ParamConfig> selectParamConfigList(ParamConfig paramConfig);

    /**
     * 根据key查询配置参数
     * @param configKey 参数配置key
     * @return 参数配置
     */
    ParamConfig selectParamConfigByKey(String configKey);

    /**
     * 新增参数配置
     *
     * @param paramConfig 参数配置
     * @return 结果
     */
    int insertParamConfig(ParamConfig paramConfig);

    /**
     * 修改参数配置
     *
     * @param paramConfig 参数配置
     * @return 结果
     */
    int updateParamConfig(ParamConfig paramConfig);

    /**
     * 删除参数配置
     *
     * @param configId 参数配置ID
     * @return 结果
     */
    int deleteParamConfigById(Long configId);

    /**
     * 批量删除参数配置
     *
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    int deleteParamConfigByIds(String[] configIds);
}
