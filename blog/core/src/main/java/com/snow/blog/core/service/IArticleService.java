package com.snow.blog.core.service;

import com.snow.blog.core.vo.ArticleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SNOW on 2018.01.25.
 */
public interface IArticleService {

    // 查
    List<ArticleVO> getArticlesAll();

    ArticleVO getArticleByArticleId(String articleId);

    Page<ArticleVO> getArticlesAllByPage(Pageable pageable);

    List<ArticleVO> getArticlesByUserId(String userId);

    Page<ArticleVO> getArticlesByUserIdAndPage(String userId, Pageable pageable);

    // 增，改
    ArticleVO saveArticle(ArticleVO articleVO);

    // 删
    void deleteArticle(ArticleVO articleVO);

}
