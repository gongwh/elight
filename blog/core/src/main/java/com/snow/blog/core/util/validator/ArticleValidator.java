package com.snow.blog.core.util.validator;

import com.snow.blog.core.vo.ArticleVO;
import com.snow.lib.enums.ResultEnum;
import com.snow.lib.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by SNOW on 2018.01.30.
 */
public class ArticleValidator {

    public static void validate(ArticleVO articleVO) {
        if (null == articleVO
                || StringUtils.isBlank(articleVO.getUserId())
                || StringUtils.isBlank(articleVO.getContentMd())
                || StringUtils.isBlank(articleVO.getContentHtml())
                || StringUtils.isBlank(articleVO.getContentText())) {
            throw new BaseException(ResultEnum.VALIDATE_ERROR);
        }
    }

    public static void articleExist(ArticleVO articleVO) {
        validate(articleVO);
        if (StringUtils.isBlank(articleVO.getArticleId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }

    public static void articleNotExist(ArticleVO articleVO) {
        validate(articleVO);
        if (StringUtils.isBlank(articleVO.getArticleId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }

}
