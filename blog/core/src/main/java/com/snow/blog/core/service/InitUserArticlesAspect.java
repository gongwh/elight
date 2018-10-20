package com.snow.blog.core.service;

import com.snow.blog.core.repository.ArticleRepository;
import com.snow.blog.core.repository.entity.Article;
import com.snow.lib.BeanCopyUtil;
import com.snow.security.core.properties.SecurityProperties;
import com.snow.security.core.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class InitUserArticlesAspect {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ArticleRepository articleRepository;

    @Pointcut("execution(" +
            "public com.snow.security.core.repository.entity.User " +
            "com.snow.security.core.service.UserService.registerNewUserAccount(com.snow.security.core.support.UserDto))"
    )
    public void registration() {
    }

    @AfterReturning(returning = "user", pointcut = "registration()")
    public void doAfterRegistration(User user) throws Throwable {
        // 处理完请求，返回内容
        log.debug("[文章] [注册监听] [用户]", user);
        try {
            if (user != null) {
                List<Article> rootUserArticles = articleService.getArticleList(securityProperties.getRootUserId(), user.getUserId());
                if (CollectionUtils.isNotEmpty(rootUserArticles)) {
                    List<Article> userArticles = BeanCopyUtil.createOnListCopy(rootUserArticles, Article.class);
                    userArticles.forEach(article -> {
                        article.setArticleId(null);
                        article.setUserId(user.getUserId());
                    });
                    List<Article> results = articleRepository.save(userArticles);
                    log.debug("[文章] [注册监听] [保存ROOT用户文章]", results);
                }
            }
        } catch (Exception e) {
            log.debug("[文章] [注册监听]", e);
        }
    }

}