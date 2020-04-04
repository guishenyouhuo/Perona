package com.guigui.perona.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.OperateLogMapper;
import com.guigui.perona.entity.OperateLog;
import com.guigui.perona.service.IOperateLogService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 操作日志记录Service业务层处理
 * 
 * @author guigui
 * @date 2020-04-02
 */
@Service
public class OperateLogServiceImpl implements IOperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 查询操作日志记录
     * 
     * @param id 操作日志记录ID
     * @return 操作日志记录
     */
    @Override
    public OperateLog selectOperateLogById(Long id) {
        return operateLogMapper.selectOperateLogById(id);
    }

    /**
     * 查询操作日志记录列表
     * 
     * @param operateLog 操作日志记录
     * @return 操作日志记录
     */
    @Override
    public List<OperateLog> selectOperateLogList(OperateLog operateLog) {
        return operateLogMapper.selectOperateLogList(operateLog);
    }

    /**
     * 新增操作日志记录
     * 
     * @param operateLog 操作日志记录
     * @return 结果
     */
    @Override
    public int insertOperateLog(OperateLog operateLog) {
        return operateLogMapper.insertOperateLog(operateLog);
    }

    /**
     * 修改操作日志记录
     * 
     * @param operateLog 操作日志记录
     * @return 结果
     */
    @Override
    public int updateOperateLog(OperateLog operateLog) {
        return operateLogMapper.updateOperateLog(operateLog);
    }

    /**
     * 删除操作日志记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOperateLogByIds(String ids) {
        return operateLogMapper.deleteOperateLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除操作日志记录信息
     * 
     * @param id 操作日志记录ID
     * @return 结果
     */
    @Override
    public int deleteOperateLogById(Long id) {
        return operateLogMapper.deleteOperateLogById(id);
    }

    @Override
    public void cleanOperateLog() {
        operateLogMapper.cleanOperateLog();
    }

}
