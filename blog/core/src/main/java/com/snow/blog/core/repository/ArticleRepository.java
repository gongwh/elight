package com.snow.blog.core.repository;

import com.snow.blog.core.repository.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by SNOW on 2018.01.25.
 */
public interface ArticleRepository extends JpaRepository<Article, String> {

    Article findByArticleIdAndUserIdAndEnabledIsTrue(String articleId,String userId);

    Page<Article> findByUserIdAndEnabledIsTrue(String userId, Pageable pageable);

    Page<Article> findByUserIdAndPersonalIsFalseAndEnabledIsTrue(String userId, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE userId = :userId AND enabled = true AND title LIKE :content")
    Page<Article> findByUserIdAndSearchLike(@Param("userId") String userId, @Param("content") String content, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE userId = :userId AND enabled = true AND personal = false  AND title LIKE :content")
    Page<Article> findByUserIdAndPersonalIsFalseSearchLike(@Param("userId") String userId, @Param("content") String content, Pageable pageable);
}
