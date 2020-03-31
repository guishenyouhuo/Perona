package com.guigui.perona.common.dto;

import com.guigui.perona.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 用于封装Article表按时间归档的DTO
 * @Author: guigui
 * @Date: 2019-10-31 11:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivesWithArticle implements Serializable {

    private static final long serialVersionUID = -1280975952384628310L;
    private String date;
    private List<Article> articles;
}
