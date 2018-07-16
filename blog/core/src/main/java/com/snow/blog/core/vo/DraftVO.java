package com.snow.blog.core.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by SNOW on 2018.01.30.
 */
@Data
public class DraftVO {

    private String draftId;

    private String articleId;
    // 标题图片
    private String titleImgUrl;
    // 标题
    private String title;
    // markdown 文本
    private String contentMd;
    // 用户ID
    private String userId;
    protected Boolean enable = true;
    protected Date createTime;
    protected Date updateTime;
}
