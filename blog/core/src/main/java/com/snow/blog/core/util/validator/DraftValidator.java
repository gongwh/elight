package com.snow.blog.core.util.validator;

import com.snow.blog.core.repository.entity.Draft;
import com.snow.lib.enums.ResultEnum;
import com.snow.lib.exception.BaseException;

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
            throw new BaseException(errInfo, ResultEnum.VALIDATE_ERROR.getCode());
        }
    }

    public static void draftExist(Draft draft) {
        validate(draft);
    }

    public static void draftNotExist(Draft draft) {
        validate(draft);
    }
}
