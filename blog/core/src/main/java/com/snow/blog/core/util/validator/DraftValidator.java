package com.snow.blog.core.util.validator;

import com.snow.blog.core.repository.entity.Draft;
import com.snow.lib.enums.ResultEnum;
import com.snow.lib.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by SNOW on 2018.01.30.
 */
public class DraftValidator {

    public static void validate(Draft draft) {
        if (null == draft
                || draft.getContentMd() == null) {
            throw new BaseException(ResultEnum.VALIDATE_ERROR);
        }
    }

    public static void validate(Draft draft, String errInfo) {
        if (null == draft
                || draft.getContentMd() == null) {
            throw new BaseException(ResultEnum.VALIDATE_ERROR.getCode(), errInfo);
        }
    }

    public static void draftExist(Draft draft) {
        validate(draft);
        if (StringUtils.isBlank(draft.getUserId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }

    public static void draftNotExist(Draft draft) {
        validate(draft);
        if (StringUtils.isNotBlank(draft.getUserId())) {
            throw new BaseException(ResultEnum.VALIDATE_NOT_EXIST);
        }
    }
}
