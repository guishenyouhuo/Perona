package com.guigui.perona.controller;

import com.guigui.perona.common.constants.CommonConstants;
import com.guigui.perona.common.exception.GlobalException;
import com.guigui.perona.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Description: 图片上传
 * @Author: guigui
 * @Date: 2019-11-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/uploadfile")
public class UploadController {

    @Value(value = "${app.resources_path}")
    private String resources_path;

    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");

            String localDate = DateUtils.dateFormat(LocalDateTime.now(), DateUtils.LOCAL_YEAR_MONTH_DAY);
            String rootPath = resources_path + "/" + CommonConstants.IMG_UPLOAD_PATH + "/" + localDate;

            /**
             * 文件路径不存在则需要创建文件路径
             */
            File filePath = new File(rootPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            //最终文件名
            String originFileName = attach.getOriginalFilename();
            if (originFileName == null || originFileName.length() == 0) {
                throw new GlobalException("原始文件名为空！");
            }
            int index = originFileName.lastIndexOf(".");
            if (index == -1) {
                throw new GlobalException("原始文件扩展名异常！");
            }
            String randomName = UUID.randomUUID().toString().replace("-", "");
            String newFileName = originFileName.substring(0, index) + randomName + originFileName.substring(index);
            File realFile = new File(rootPath + File.separator + newFileName);
            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);

            response.setCharacterEncoding("UTF-8");
            String url = CommonConstants.IMG_UPLOAD_PATH + "/" + localDate + "/" + newFileName;
            //下面response返回的json格式是editor.md所限制的，规范输出就OK
            response.getWriter().write("{\"success\": 1, \"message\":\"上传成功\",\"url\":\"/" + url + "\"}");
        } catch (Exception e) {
            log.error("上传图片出现异常！imgName: {}", attach.getOriginalFilename(), e);
            try {
                response.getWriter().write("{\"success\":0, \"message\":\"" + e.getMessage() + "\"}");
            } catch (IOException e1) {
                log.error("上传完成后返回到出现异常！imgName: {}", attach.getOriginalFilename(), e);
            }
        }
    }
}
