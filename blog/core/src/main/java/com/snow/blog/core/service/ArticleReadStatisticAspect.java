package com.snow.blog.core.service;

import com.snow.blog.core.repository.ArticleRepository;
import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.Statistic;
import com.snow.blog.core.vo.ArticleVO;
import com.snow.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ArticleReadStatisticAspect {

    @Autowired
    private ArticleRepository articleRepository;

    @Pointcut("execution(" +
            "com.snow.blog.core.repository.entity.Article " +
            "com.snow.blog.core.repository.ArticleRepository.findByArticleIdAndEnabledIsTrue(..))"
    )
    public void findByArticleIdAndEnabledIsTrue() {
    }

    @AfterReturning(returning = "article", pointcut = "findByArticleIdAndEnabledIsTrue()")
    public void doAfterGetArticleById(Article article) throws Throwable {
        // 处理完请求，返回内容
        log.debug("[文章] [获取监听] [文章]", article);
        try {
            if (null != article) {
                if (null == article.getReadStatistic()) {
                    article.setReadStatistic(new Statistic());
                }
                article.getReadStatistic().setTotal(article.getReadStatistic().getTotal() + 1);
                articleRepository.save(article);
            }
        } catch (Exception e) {
            log.debug("[文章] [获取监听]", e);
        }
    }

}