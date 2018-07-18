package com.snow.blog.core.service.impl;

import com.snow.blog.core.repository.ArticleRepository;
import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.util.validator.CommonValidator;
import com.snow.lib.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by SNOW on 2018.01.25.
 */
@Service
public class ArticleService implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> getArticlesByUserId(String userId) {
        List<Article> articles = articleRepository.findByUserIdAndEnableIsTrueOrderByUpdateTimeDesc(userId);
        return articles;
    }

    @Override
    public Page<Article> getArticlesByUserIdAndPage(String userId, Pageable pageable) {
        Page<Article> page = articleRepository.findByUserIdAndEnableIsTrue(userId, pageable);
        return page.map(e -> BeanCopyUtil.createOnCopy(e, Article.class));
    }

    @Override
    public List<Article> getArticlesAll() {
        List<Article> list = articleRepository.findByEnableIsTrue();
        return BeanCopyUtil.createOnListCopy(list, Article.class);
    }

    @Override
    public Article getArticleByArticleId(String articleId) {
        Article article = articleRepository.findByArticleIdAndEnableIsTrue(articleId);
        CommonValidator.getOk(article);
        return BeanCopyUtil.createOnCopy(article, Article.class);
    }

    @Override
    public Page<Article> getArticlesAllByPage(Pageable pageable) {
        Page<Article> page = articleRepository.findByEnableIsTrue(pageable);
        return page.map(e -> BeanCopyUtil.createOnCopy(e, Article.class));
    }

    @Override
    public Article saveArticle(Article article) {
        Assert.hasText(article.getUserId(), "文章用户ID不存在");
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
    public void deleteArticle(Article article) {
        article.setEnable(false);
        Article result = articleRepository.save(article);
        CommonValidator.delOk(result);
    }


}
