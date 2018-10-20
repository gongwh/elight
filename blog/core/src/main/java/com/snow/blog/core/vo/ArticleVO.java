package com.snow.blog.core.vo;

import com.snow.blog.core.repository.entity.*;
import com.snow.lib.result.BaseVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleVO extends BaseVO {

    public ArticleVO() {
    }

    public ArticleHtml getArticleHtml() {
        ArticleHtml articleHtml = new ArticleHtml();
        articleHtml.setArticleId(this.articleId);
        articleHtml.setContentHtml(this.contentHtml);
        return articleHtml;
    }

    public ArticleMd getArticleMd() {
        ArticleMd articleMd = new ArticleMd();
        articleMd.setArticleId(this.articleId);
        articleMd.setContentMd(this.contentMd);
        return articleMd;
    }

    public Article getArticle() {
        Article article = new Article();
        article.setEnabled(this.enabled);
        article.setCreateTime(this.createTime);
        article.setUpdateTime(this.updateTime);
        article.setArticleId(this.articleId);
        article.setTitleImgUrl(this.titleImgUrl);
        article.setTitle(this.title);
        article.setTitleLetter(this.titleLetter);
        article.setContentTextSubNail(this.contentTextSubNail);
        article.setUserId(this.userId);
        article.setPersonal(this.personal);
        article.setReadStatistic(this.readStatistic);
        article.setTags(this.tags);
        article.setCategories(this.categories);
        return article;
    }

    public ArticleVO(Article article, ArticleHtml articleHtml, ArticleMd articleMd) {
        this.setEnabled(article.getEnabled());
        this.setCreateTime(article.getCreateTime());
        this.setUpdateTime(article.getUpdateTime());
        this.setArticleId(article.getArticleId());
        this.setTitleImgUrl(article.getTitleImgUrl());
        this.setTitle(article.getTitle());
        this.setTitleLetter(article.getTitleLetter());
        this.setContentTextSubNail(article.getContentTextSubNail());
        this.setUserId(article.getUserId());
        this.setPersonal(article.getPersonal());
        this.setReadStatistic(article.getReadStatistic());
        this.setTags(article.getTags());
        this.setCategories(article.getCategories());
        this.setContentHtml(articleHtml.getContentHtml());
        this.setContentMd(articleMd.getContentMd());
    }

    private String articleId;

    // 标题图片
    private String titleImgUrl;

    // 标题
    private String title;

    // 标题拼音
    private String titleLetter;

    // html 文本缩略
    private String contentTextSubNail;

    // 用户ID
    private String userId;

    // 是否私有
    private Boolean personal;

    // 阅读统计
    private Statistic readStatistic;

    // 文章标签
    private List<Tag> tags = new ArrayList<>();

    // 文章分类
    private List<Category> categories = new ArrayList<>();

    // html 文本
    private String contentHtml;

    // markdown 文本
    private String contentMd;

    // html 文本
    private String contentText;
}
