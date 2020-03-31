package com.guigui.perona.service;

import com.guigui.perona.entity.ParamConfig;

import java.util.List;

/**
 * 参数配置Service接口
 *
 * @author guigui
 * @date 2020-03-13
 */
public interface IParamConfigService {
    /**
     * 查询参数配置
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
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String selectParamConfigByKey(String configKey);

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
     * 批量删除参数配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteParamConfigByIds(String ids);

    /**
     * 删除参数配置信息
     *
     * @param configId 参数配置ID
     * @return 结果
     */
    int deleteParamConfigById(Long configId);

    /**
     * 校验参数键名是否唯一
     *
     * @param paramConfig 参数信息
     * @return 结果
     */
    String checkConfigKeyUnique(ParamConfig paramConfig);

}
