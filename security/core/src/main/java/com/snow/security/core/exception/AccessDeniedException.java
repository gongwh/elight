package com.snow.security.core.exception;

import com.snow.lib.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BaseException {

    private AccessDeniedException() {
        super(HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException(String msg) {
        super(msg, HttpStatus.FORBIDDEN.value());
    }
}
