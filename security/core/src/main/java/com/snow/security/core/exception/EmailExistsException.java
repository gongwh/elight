package com.snow.security.core.exception;

import com.snow.lib.exception.BaseException;

/**
 * @create by SNOW 2018.07.12
 */
public class EmailExistsException extends BaseException {
    public EmailExistsException(Integer code, String msg) {
        super(code, msg);
    }
}
