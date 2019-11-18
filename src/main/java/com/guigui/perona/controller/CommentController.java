package com.guigui.perona.controller;


import com.guigui.perona.common.annotation.Log;
import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.AddressUtil;
import com.guigui.perona.common.utils.IPUtil;
import com.guigui.perona.common.utils.QueryPage;
import com.guigui.perona.common.utils.Return;
import com.guigui.perona.entity.Comment;
import com.guigui.perona.service.ICommentService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigui.perona.common.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author guigui
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/api/comment")
@Api(value = "CommentController", tags = {"评论管理接口"})
public class CommentController extends BaseController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/list")
    public Return list(Comment comment, QueryPage queryPage) {
        return new Return<>(super.getData(commentService.list(comment, queryPage)));
    }

    @GetMapping("/{id}")
    public Return findById(@PathVariable Long id) {
        return new Return<>(commentService.getById(id));
    }

    @PostMapping
    public Return save(@RequestBody Comment comment, HttpServletRequest request) {
        try {
            String ip = IPUtil.getIpAddr(request);
            comment.setTime(LocalDateTime.now());
            comment.setIp(ip);
            comment.setAddress(AddressUtil.getAddress(ip));
            String header = request.getHeader(CommonConstants.USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            comment.setDevice(browser.getName() + "," + operatingSystem.getName());
            commentService.add(comment);
            return new Return();
        } catch (Exception e) {
            log.error("新增评论出现异常！comment: {}", comment.getContent(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新评论")
    public Return update(@RequestBody Comment comment) {
        try {
            commentService.update(comment);
            return new Return();
        } catch (Exception e) {
            log.error("更新评论出现异常！comment: {}", comment.getContent(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除评论")
    public Return delete(@PathVariable Long id) {
        try {
            commentService.delete(id);
            return new Return();
        } catch (Exception e) {
            log.error("删除评论出现异常！commentId: {}", id, e);
            throw new GlobalException(e.getMessage());
        }
    }
}
