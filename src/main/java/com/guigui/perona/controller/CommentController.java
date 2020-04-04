package com.guigui.perona.controller;

import java.util.List;

import com.guigui.perona.common.aspect.annotation.OperaLog;
import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.enums.BusinessType;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.AddressUtils;
import com.guigui.perona.common.utils.DateUtils;
import com.guigui.perona.common.utils.IPUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.guigui.perona.entity.Comment;
import com.guigui.perona.service.ICommentService;
import com.guigui.perona.common.BaseController;
import com.guigui.perona.manage.web.domain.AjaxResult;
import com.guigui.perona.manage.web.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 评论Controller
 * 
 * @author guigui
 * @date 2020-03-26
 */
@Slf4j
@Controller
@RequestMapping({"/manage/comment", ""})
public class CommentController extends BaseController {
    private String prefix = "manage/blog/comment";

    @Autowired
    private ICommentService commentService;

    @GetMapping()
    public String comment() {
        return prefix + "/comment";
    }

    /**
     * 查询评论列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Comment comment) {
        startPage();
        List<Comment> list = commentService.selectCommentList(comment);
        return getDataTable(list);
    }

    /**
     * 新增保存评论
     */
    @OperaLog(businessName = "评论管理", businessType = BusinessType.INSERT)
    @PostMapping("/api/comment")
    @ResponseBody
    public AjaxResult addSave(@RequestBody Comment comment, HttpServletRequest request) {
        try {
            String ip = IPUtils.getIpAddr(request);
            comment.setTime(DateUtils.getNowDate());
            comment.setIp(ip);
            comment.setAddress(AddressUtils.getAddress(ip));
            String header = request.getHeader(CommonConstants.USER_AGENT);
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            comment.setDevice(browser.getName() + "," + operatingSystem.getName());
            return toAjax(commentService.insertComment(comment));
        } catch (Exception e) {
            log.error("新增评论出现异常！comment: {}", comment.getContent(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    /**
     * 修改评论
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Comment comment = commentService.selectCommentById(id);
        mmap.put("comment", comment);
        return prefix + "/edit";
    }

    /**
     * 修改保存评论
     */
    @OperaLog(businessName = "评论管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Comment comment) {
        return toAjax(commentService.updateComment(comment));
    }

    /**
     * 删除评论
     */
    @OperaLog(businessName = "评论管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(commentService.deleteCommentByIds(ids));
    }

}
