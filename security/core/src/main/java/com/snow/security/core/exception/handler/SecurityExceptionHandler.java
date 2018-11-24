package com.snow.security.core.exception.handler;

import com.snow.lib.result.ResultUtil;
import com.snow.lib.result.ResultVO;
import com.snow.security.core.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@Slf4j
public class SecurityExceptionHandler {
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultVO handleArrayIndexOutOfBoundsException(AccessDeniedException e) {
        log.error("[Exception] " + e);
        return ResultUtil.error(e);
    }
}
