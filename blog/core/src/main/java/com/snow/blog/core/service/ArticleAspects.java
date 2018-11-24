package com.snow.blog.core.service;

import com.snow.blog.core.repository.ArticleRepository;
import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ArticleAspects {

    @Autowired
    private ArticleRepository articleRepository;

    // 1 文章阅读统计
    @Pointcut("execution(" +
            "com.snow.blog.core.vo.ArticleVO " +
            "com.snow.blog.core.service.IArticleService.getArticleById(..))"
    )
    public void getArticleById() {
    }

    @AfterReturning(returning = "articleVO", pointcut = "getArticleById()")
    public void doAfterGetArticleById(ArticleVO articleVO) throws Throwable {
        // 处理完请求，返回内容
        log.debug("[文章] [获取监听] [文章]", articleVO);
        try {
            if (null != articleVO) {
                if (null == articleVO.getReadTotalTimes()) {
                    articleVO.setReadTotalTimes(0);
                }
                articleVO.setReadTotalTimes(articleVO.getReadTotalTimes() + 1);
                articleRepository.save(articleVO.getArticle());
            }
        } catch (Exception e) {
            log.debug("[文章] [获取监听]", e);
        }
    }
}