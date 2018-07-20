package com.snow.blog.core.service.impl;

import com.snow.blog.core.repository.ArticleRepository;
import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.util.validator.CommonValidator;
import com.snow.lib.BeanCopyUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Created by SNOW on 2018.01.25.
 */
@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public Page<Article> getArticlePage(String targetUserId, String currentUserId, Pageable pageable) {
        Page<Article> page;
        // 判断目标用户ID和当前用户是否相同
        if (StringUtils.equals(currentUserId, targetUserId)) {
            // 加载所有文章
            page = articleRepository.findByUserIdAndEnabledIsTrue(targetUserId, pageable);
        } else {
            // 加载非私有文章
            page = articleRepository.findByUserIdAndPersonalIsFalseAndEnabledIsTrue(targetUserId, pageable);
        }
        return page;
    }

    @Override
    public Article getArticleById(String articleId, String userId) {
        Article article = articleRepository.findByArticleIdAndUserIdAndEnabledIsTrue(articleId, userId);
        if (article.getPersonal() && !StringUtils.equals(article.getUserId(),userId)) {
            throw new AccessDeniedException("私有文章，无法访问");
        }
        CommonValidator.getOk(article);
        return BeanCopyUtil.createOnCopy(article, Article.class);
    }

    @Override
    public Article saveArticle(Article article, String userId) {
        article.setUserId(userId);
        Set<Tag> tagSet = article.getTags();
        if (!CollectionUtils.isEmpty(tagSet)) {
            tagSet.forEach((tag -> {
                tag.setUserId(article.getUserId());
            }));
        }
        Article result = articleRepository.save(article);
        CommonValidator.saveOk(result);
        return result;
    }

    @Override
    public void deleteArticle(Article article, String userId) {
        Article result = articleRepository.findByArticleIdAndUserIdAndEnabledIsTrue(article.getArticleId(), userId);
        if (null != result) {
            result.setEnabled(false);
            result = articleRepository.save(result);
        }
        CommonValidator.delOk(result);
    }


}
