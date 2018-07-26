package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SNOW on 2018.01.25.
 */
public interface IArticleService {

    // 分页查
    Page<Article> getArticlePage(String targetUserId,String currentUserId,Pageable pageable);

    // 单个查
    Article getArticleById(String articleId,String userId);

    // 增，改
    Article saveArticle(Article article,String userId);

    // 删
    void deleteArticle(Article article,String userId);

    Page<Article> getArticleSearchPage(String targetUserId,String currentUserId,String content,Pageable pageable);
}
