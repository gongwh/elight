package com.snow.blog.core.util.validator;

import com.snow.blog.core.repository.entity.Article;
import com.snow.lib.enums.ResultEnum;
import com.snow.lib.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by SNOW on 2018.01.30.
 */
public class ArticleValidator {

    public static void validate(Article article) {
        if (null == article
                || StringUtils.isBlank(article.getContentMd())
                || StringUtils.isBlank(article.getContentHtml())
                || StringUtils.isBlank(article.getContentText())) {
            throw new BaseException(-1, "文章不符合条件");
        }
    }

    public static void articleExist(Article article) {
        validate(article);
        if (StringUtils.isBlank(article.getArticleId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }

    public static void articleNotExist(Article article) {
        validate(article);
        if (StringUtils.isBlank(article.getArticleId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }

}
