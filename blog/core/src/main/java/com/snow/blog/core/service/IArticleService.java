package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.web.controller.support.SearchArticleCondition;
import com.snow.security.core.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SNOW on 2018.01.25.
 */
public interface IArticleService {

    // 分页查
    Page<Article> getArticlePage(String targetUserId, String currentUserId, Pageable pageable);

    // 单个查
    Article getArticleById(String articleId, String userId);

    // 增，改
    Article saveArticle(Article article, String userId);

    // 删
    void deleteArticle(Article article);

    // 根据条件搜索文章
    Page<Article> searchArticleByCondition (SearchArticleCondition condition, String currentUserId, Pageable pageable);
}
