package com.guigui.perona.controller;


import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.LoginLog;
import com.guigui.perona.service.ILoginLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-25
 */
@RestController
@RequestMapping("/api/loginlog")
@Api(value = "LoginLogController", tags = {"登录日志管理接口"})
public class LoginLogController extends BaseController {

    @Autowired
    private ILoginLogService loginLogService;

    @GetMapping("/list")
    public Return findByPage(LoginLog loginLog, QueryPage queryPage) {
        return new Return<>(super.getData(loginLogService.list(loginLog, queryPage)));
    }

    @Log("删除登录日志")
    @DeleteMapping("/{id}")
    public Return delete(@PathVariable Long id) {
        try {
            loginLogService.delete(id);
            return new Return();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }
}
