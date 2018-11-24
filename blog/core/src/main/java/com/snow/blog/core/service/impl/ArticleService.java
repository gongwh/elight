package com.snow.blog.core.service.impl;

import com.snow.blog.core.properties.BlogProperties;
import com.snow.blog.core.repository.ArticleHtmlRepository;
import com.snow.blog.core.repository.ArticleMdRepository;
import com.snow.blog.core.repository.ArticleRepository;
import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.ArticleHtml;
import com.snow.blog.core.repository.entity.ArticleMd;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.service.ITagService;
import com.snow.blog.core.util.Pinyin4jUtil;
import com.snow.blog.core.util.validator.CommonValidator;
import com.snow.blog.core.vo.ArticleVO;
import com.snow.blog.core.web.controller.support.SearchArticleCondition;
import com.snow.security.core.exception.AccessDeniedException;
import com.snow.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private ArticleMdRepository articleMdRepository;

    @Autowired
    private ArticleHtmlRepository articleHtmlRepository;

    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private ITagService tagService;

    private String rootUserId;

    public void setRootUserId(@Autowired SecurityProperties securityProperties) {
        rootUserId = securityProperties.getRootUserId();
    }


    @Override
    public List<Article> getArticleList(String targetUserId, String currentUserId) {
        List<Article> articles;
        // 判断目标用户ID和当前用户是否相同
        if (StringUtils.equals(targetUserId, currentUserId)) {
            // 加载所有文章
            articles = articleRepository.findDistinctByUserIdAndEnabledIsTrue(targetUserId);
        } else {
            // 加载非私有文章
            articles = articleRepository.findDistinctByUserIdAndPersonalIsFalseAndEnabledIsTrue(targetUserId);
        }
        return articles;
    }

    @Override
    public Page<Article> getArticlePage(String targetUserId, String currentUserId, Pageable pageable) {
        Page<Article> page;
        // 判断目标用户ID和当前用户是否相同
        if (StringUtils.equals(currentUserId, targetUserId)) {
            // 加载所有文章
            page = articleRepository.findDistinctByUserIdAndEnabledIsTrue(targetUserId, pageable);
        } else {
            // 加载非私有文章
            page = articleRepository.findDistinctByUserIdAndPersonalIsFalseAndEnabledIsTrue(targetUserId, pageable);
        }
        return page;
    }

    @Override
    public ArticleVO getArticleById(String articleId, String userId) {
        Article article = articleRepository.findByArticleIdAndEnabledIsTrue(articleId);
        if (null != article && !StringUtils.equals(article.getUserId(), userId) && article.getPersonal()) {
            throw new AccessDeniedException("私有文章，无法访问");
        }
        ArticleHtml articleHtml = articleHtmlRepository.getOne(articleId);
        ArticleMd articleMd = articleMdRepository.getOne(articleId);
        return new ArticleVO(article, articleHtml, articleMd);
    }

    @Override
    public ArticleVO saveArticle(ArticleVO articleVO, String userId) {
        articleVO.setUserId(userId);
        if (StringUtils.isBlank(articleVO.getArticleId())) {
            articleVO.setArticleId(UUID.randomUUID().toString());
        }
        // 保存标签
        List<Tag> tags = tagService.saveTag(articleVO.getTags(), userId);
        articleVO.setTags(tags);
        // 生成标题拼音
        if (null != articleVO.getTitle()) {
            try {
                articleVO.setTitleLetter(StringUtils.substring(Pinyin4jUtil.converterToFirstSpell(articleVO.getTitle()).toUpperCase(), 0, 1));
            } catch (Exception e) {
                log.error("[文章保存] [标题字母] 生成", e);
            }
        }
        // 生成缩略文字
        String thumbnail = StringUtils.deleteWhitespace(articleVO.getContentText());
        thumbnail = thumbnail.replaceAll(IMAGE_URL_REGEX, IMAGE_URL_REPLACEMENT);
        thumbnail = StringUtils.substring(thumbnail, 0, blogProperties.getArticle().getThumbnailCharNum());
        log.debug("[文章保存] 缩略文字 {}", thumbnail);
        articleVO.setContentTextSubNail(thumbnail);
        // 更新最后修改日期
        articleVO.setLatestModifyDate(new Date());
        // 保存
        Article articleToSave = articleVO.getArticle();
        Article articleSaveResult = articleRepository.save(articleToSave);
        CommonValidator.saveOk(articleSaveResult);

        ArticleHtml articleHtmlToSave = articleVO.getArticleHtml();
        ArticleHtml articleHtmlSaveResult = articleHtmlRepository.save(articleHtmlToSave);
        CommonValidator.saveOk(articleHtmlSaveResult);

        ArticleMd articleMdToSave = articleVO.getArticleMd();
        ArticleMd articleMdSaveResult = articleMdRepository.save(articleMdToSave);
        CommonValidator.saveOk(articleMdSaveResult);

        return new ArticleVO(articleSaveResult, articleHtmlSaveResult, articleMdSaveResult);
    }

    @Override
    public void deleteArticle(ArticleVO articleVO) {
        if (null != articleVO) {
            articleRepository.delete(articleVO.getArticleId());
        }
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
                    return articleRepository.findDistinctByUserIdAndTitleLikeAndTags_NameInAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), condition.getTagNames(), pageable);
                } else {
                    return articleRepository.findDistinctByUserIdAndTitleLikeAndTags_NameInAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), condition.getTagNames(), pageable);
                }
            } else {
                if (notCurrentUser) {
                    return articleRepository.findByUserIdAndTags_NameInAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), condition.getTagNames(), pageable);
                } else {
                    return articleRepository.findDistinctByUserIdAndTags_NameInAndEnabledIsTrue(condition.getUserId(), condition.getTagNames(), pageable);
                }
            }
        } else {
            if (needTitleSearch) {
                if (notCurrentUser) {
                    return articleRepository.findDistinctByUserIdAndTitleLikeAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), pageable);
                } else {
                    return articleRepository.findDistinctByUserIdAndTitleLikeAndEnabledIsTrue(condition.getUserId(), condition.getTitle(), pageable);
                }
            } else {
                if (notCurrentUser) {
                    return articleRepository.findDistinctByUserIdAndPersonalIsFalseAndEnabledIsTrue(condition.getUserId(), pageable);
                } else {
                    return articleRepository.findDistinctByUserIdAndEnabledIsTrue(condition.getUserId(), pageable);
                }
            }
        }
    }

}
