package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SNOW on 2018.01.25.
 */
public interface IArticleService {

    // 查
    List<Article> getArticlesAll();

    Article getArticleByArticleId(String articleId);

    Page<Article> getArticlesAllByPage(Pageable pageable);

    List<Article> getArticlesByUserId(String userId);

    Page<Article> getArticlesByUserIdAndPage(String userId, Pageable pageable);

    // 增，改
    Article saveArticle(Article article);

    // 删
    void deleteArticle(Article article);

}
