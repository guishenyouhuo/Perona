package com.guigui.perona.service.impl;

import java.util.List;
import com.guigui.perona.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guigui.perona.mapper.LoginLogMapper;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.service.ILoginLogService;
import com.guigui.perona.common.utils.text.Convert;

/**
 * 登陆日志Service业务层处理
 * 
 * @author guigui
 * @date 2020-03-31
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public LoginLog selectLoginLogById(Long id) {
        return loginLogMapper.selectLoginLogById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param loginLog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<LoginLog> selectLoginLogList(LoginLog loginLog) {
        return loginLogMapper.selectLoginLogList(loginLog);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param loginLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertLoginLog(LoginLog loginLog) {
        loginLog.setCreateTime(DateUtils.getNowDate());
        return loginLogMapper.insertLoginLog(loginLog);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param loginLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateLoginLog(LoginLog loginLog) {
        return loginLogMapper.updateLoginLog(loginLog);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLoginLogByIds(String ids) {
        return loginLogMapper.deleteLoginLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteLoginLogById(Long id) {
        return loginLogMapper.deleteLoginLogById(id);
    }

}
