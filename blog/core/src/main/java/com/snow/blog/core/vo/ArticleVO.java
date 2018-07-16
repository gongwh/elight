package com.snow.blog.core.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * Created by SNOW on 201 8.01.25.
 */
@Data
public class ArticleVO {
    private String articleId;
    // 标题图片
    private String titleImgUrl;
    // 标题
    private String title;
    // html 文本
    private String contentHtml;
    // markdown 文本
    private String contentMd;
    // text 文本
    private String contentText;
    // 用户ID
    private String userId;

    protected Boolean enable = true;
    protected Date createTime = new Date();
    protected Date updateTime = new Date();
}
