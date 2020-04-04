package com.guigui.perona.mapper;

import com.guigui.perona.entity.OperateLog;

import java.util.List;

/**
 * 操作日志记录Mapper接口
 *
 * @author guigui
 * @date 2020-04-02
 */
public interface OperateLogMapper {
    /**
     * 根据主键查询操作日志记录
     *
     * @param id 操作日志记录ID
     * @return 操作日志记录
     */
    OperateLog selectOperateLogById(Long id);

    /**
     * 查询操作日志记录列表
     *
     * @param operateLog 操作日志记录
     * @return 操作日志记录集合
     */
    List<OperateLog> selectOperateLogList(OperateLog operateLog);

    /**
     * 新增操作日志记录
     *
     * @param operateLog 操作日志记录
     * @return 结果
     */
    int insertOperateLog(OperateLog operateLog);

    /**
     * 修改操作日志记录
     *
     * @param operateLog 操作日志记录
     * @return 结果
     */
    int updateOperateLog(OperateLog operateLog);

    /**
     * 删除操作日志记录
     *
     * @param id 操作日志记录ID
     * @return 结果
     */
    int deleteOperateLogById(Long id);

    /**
     * 批量删除操作日志记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOperateLogByIds(String[] ids);

    /**
     * 清空表
     */
    int cleanOperateLog();
}
