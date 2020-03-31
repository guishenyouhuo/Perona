package com.guigui.perona.service.impl;

import java.util.List;

import com.guigui.perona.common.constants.UserConstants;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.ShiroUtils;
import com.guigui.perona.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.ParamConfigMapper;
import com.guigui.perona.entity.ParamConfig;
import com.guigui.perona.service.IParamConfigService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 参数配置Service业务层处理
 *
 * @author guigui
 * @date 2020-03-13
 */
@Service
public class ParamConfigServiceImpl implements IParamConfigService {

    @Autowired
    private ParamConfigMapper paramConfigMapper;

    /**
     * 查询参数配置
     *
     * @param configId 参数配置ID
     * @return 参数配置
     */
    @Override
    public ParamConfig selectParamConfigById(Long configId) {
        return paramConfigMapper.selectParamConfigById(configId);
    }

    /**
     * 查询参数配置列表
     *
     * @param paramConfig 参数配置
     * @return 参数配置
     */
    @Override
    public List<ParamConfig> selectParamConfigList(ParamConfig paramConfig) {
        return paramConfigMapper.selectParamConfigList(paramConfig);
    }

    @Override
    public String selectParamConfigByKey(String configKey) {
        ParamConfig retConfig = paramConfigMapper.selectParamConfigByKey(configKey);
        return StringUtils.isNotNull(retConfig) ? retConfig.getConfigValue() : "";
    }

    /**
     * 新增参数配置
     *
     * @param paramConfig 参数配置
     * @return 结果
     */
    @Override
    public int insertParamConfig(ParamConfig paramConfig) {
        paramConfig.setCreateTime(DateUtils.getNowDate());
        paramConfig.setCreateBy(ShiroUtils.getLoginName());
        return paramConfigMapper.insertParamConfig(paramConfig);
    }

    /**
     * 修改参数配置
     *
     * @param paramConfig 参数配置
     * @return 结果
     */
    @Override
    public int updateParamConfig(ParamConfig paramConfig) {
        paramConfig.setUpdateTime(DateUtils.getNowDate());
        paramConfig.setUpdateBy(ShiroUtils.getLoginName());
        return paramConfigMapper.updateParamConfig(paramConfig);
    }

    /**
     * 删除参数配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteParamConfigByIds(String ids) {
        return paramConfigMapper.deleteParamConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除参数配置信息
     *
     * @param configId 参数配置ID
     * @return 结果
     */
    @Override
    public int deleteParamConfigById(Long configId) {
        return paramConfigMapper.deleteParamConfigById(configId);
    }

    @Override
    public String checkConfigKeyUnique(ParamConfig paramConfig) {
        long configId = paramConfig.getConfigId() == null ? -1L : paramConfig.getConfigId();
        ParamConfig info = paramConfigMapper.selectParamConfigByKey(paramConfig.getConfigKey());
        if (info != null && info.getConfigId() != configId) {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }

}
