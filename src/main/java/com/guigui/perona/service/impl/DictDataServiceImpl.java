package com.guigui.perona.service.impl;

import java.util.List;

import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.DictDataMapper;
import com.guigui.perona.entity.DictData;
import com.guigui.perona.service.IDictDataService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 字典数据Service业务层处理
 *
 * @author guigui
 * @date 2020-03-13
 */
@Service
public class DictDataServiceImpl implements IDictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 查询字典数据
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public DictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 查询字典数据列表
     *
     * @param dictData 字典数据
     * @return 字典数据
     */
    @Override
    public List<DictData> selectDictDataList(DictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    @Override
    public DictData selectDictByTypeAndVal(String dictType, String dictValue) {
        return dictDataMapper.selectDictByTypeAndVal(dictType, dictValue);
    }

    /**
     * 新增字典数据
     *
     * @param dictData 字典数据
     * @return 结果
     */
    @Override
    public int insertDictData(DictData dictData) {
        dictData.setCreateTime(DateUtils.getNowDate());
        dictData.setCreateBy(ShiroUtils.getLoginName());
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 修改字典数据
     *
     * @param dictData 字典数据
     * @return 结果
     */
    @Override
    public int updateDictData(DictData dictData) {
        dictData.setUpdateTime(DateUtils.getNowDate());
        dictData.setUpdateBy(ShiroUtils.getLoginName());
        return dictDataMapper.updateDictData(dictData);
    }

    /**
     * 删除字典数据对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(String ids) {
        return dictDataMapper.deleteDictDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除字典数据信息
     *
     * @param dictCode 字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataById(Long dictCode) {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

}
