package com.snow.blog.core.service.impl;

import com.snow.blog.core.properties.BlogProperties;
import com.snow.blog.core.repository.ArticleRepository;
import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.service.ITagService;
import com.snow.blog.core.util.validator.CommonValidator;
import com.snow.blog.core.web.controller.support.SearchArticleCondition;
import com.snow.security.core.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by SNOW on 2018.01.25.
 */
@Service
@Slf4j
public class ArticleService implements IArticleService {

    private static final String IMAGE_URL_REGEX = "\\[http(s)?:.+?\\.((jpg)|(jpeg)|(png)|(bmp))\\]";

    private static final String IMAGE_URL_REPLACEMENT = "\n";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private ITagService tagService;


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
        Article article = articleRepository.findByArticleIdAndEnabledIsTrue(articleId);
        if (null != article && !StringUtils.equals(article.getUserId(), userId) && article.getPersonal()) {
            throw new AccessDeniedException("私有文章，无法访问");
        }
        return article;
    }

    @Override
    public Article saveArticle(Article article, String userId) {
        article.setUserId(userId);
        // 保存标签
        List<Tag> tags = tagService.saveTag(article.getTags(), userId);
        article.setTags(tags);
        // 生成缩略文字
        String thumbnail = StringUtils.deleteWhitespace(article.getContentText());
        thumbnail = thumbnail.replaceAll(IMAGE_URL_REGEX, IMAGE_URL_REPLACEMENT);
        thumbnail = StringUtils.substring(thumbnail, 0, blogProperties.getArticle().getThumbnailCharNum());
        log.debug("[文章保存] 缩略文字 {}", thumbnail);
        article.setContentTextSubNail(thumbnail);
        // 保存
        Article result = articleRepository.save(article);
        CommonValidator.saveOk(result);
        return result;
    }

    @Override
    public void deleteArticle(Article article) {
        //        Article result = articleRepository.findByArticleIdAndEnabledIsTrue(article.getArticleId());
        if (null != article) {
            //            result.setEnabled(false);
            articleRepository.delete(article.getArticleId());
        }
        //        CommonValidator.delOk(result);
    }

    @Override
    public Page<Article> searchArticleByCondition(SearchArticleCondition condition, String currentUserId, Pageable pageable) {
        boolean notCurrentUser = !StringUtils.equals(currentUserId, condition.getUserId());
        boolean needTagSearch = !CollectionUtils.isEmpty(condition.getTagNames());
        boolean needTitleSearch = StringUtils.isNotBlank(condition.getTitle());
        if (needTitleSearch) {
            condition.setTitle("%" + condition.getTitle() + "%");
        }
        if (needTagSearch) {
            if (needTitleSearch) {
                if (notCurrentUser) {
                    return articleRepository.findByUserIdAndTitleLikeAndTags_NameInAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), condition.getTagNames(), pageable);
                } else {
                    return articleRepository.findByUserIdAndTitleLikeAndTags_NameInAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), condition.getTagNames(), pageable);
                }
            } else {
                if (notCurrentUser) {
                    return articleRepository.findByUserIdAndTags_NameInAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), condition.getTagNames(), pageable);
                } else {
                    return articleRepository.findByUserIdAndTags_NameInAndEnabledIsTrue(condition.getUserId(), condition.getTagNames(), pageable);
                }
            }
        } else {
            if (needTitleSearch) {
                if (notCurrentUser) {
                    return articleRepository.findByUserIdAndTitleLikeAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), pageable);
                } else {
                    return articleRepository.findByUserIdAndTitleLikeAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), pageable);
                }
            } else {
                if (notCurrentUser) {
                    return articleRepository.findByUserIdAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), pageable);
                } else {
                    return articleRepository.findByUserIdAndEnabledIsTrue(condition.getUserId(), pageable);
                }
            }
        }
    }

}
