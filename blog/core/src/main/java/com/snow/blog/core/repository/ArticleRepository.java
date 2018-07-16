package com.snow.blog.core.repository;

import com.snow.blog.core.repository.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created by SNOW on 2018.01.25.
 */
public interface ArticleRepository extends JpaRepository<Article, String> {

    List<Article> findByEnableIsTrue();

    Page<Article> findByEnableIsTrue(Pageable pageable);

    Article findByArticleIdAndEnableIsTrue(String articleId);

    Page<Article> findByUserIdAndEnableIsTrue(String userId, Pageable pageable);

    List<Article> findByUserIdAndEnableIsTrueOrderByUpdateTimeDesc(String userId);
}
