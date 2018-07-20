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

    Article findByArticleIdAndUserIdAndEnabledIsTrue(String articleId,String userId);

    Page<Article> findByUserIdAndEnabledIsTrue(String userId, Pageable pageable);

    Page<Article> findByUserIdAndPersonalIsFalseAndEnabledIsTrue(String userId, Pageable pageable);
}
