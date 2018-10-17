package com.snow.blog.core.util.validator;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.vo.ArticleVO;
import com.snow.lib.enums.ResultEnum;
import com.snow.lib.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by SNOW on 2018.01.30.
 */
public class ArticleValidator {

    public static void validate(ArticleVO article) {
        if (null == article
                || StringUtils.isBlank(article.getContentMd())
                || StringUtils.isBlank(article.getContentHtml())) {
            throw new BaseException("文章不符合条件");
        }
    }

    public static void articleExist(ArticleVO article) {
        validate(article);
        if (StringUtils.isBlank(article.getArticleId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }

    public static void articleNotExist(ArticleVO article) {
        validate(article);
        if (StringUtils.isBlank(article.getArticleId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }

}
