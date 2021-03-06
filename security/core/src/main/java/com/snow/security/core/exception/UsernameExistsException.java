package com.snow.security.core.exception;

import com.snow.lib.exception.BaseException;

/**
 * @create by SNOW 2018.07.12
 */
public class UsernameExistsException extends BaseException {
    public UsernameExistsException(String username) {
        super("用户" + username + "名已存在");
    }
}
