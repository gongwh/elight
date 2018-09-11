package com.snow.lib.result;

import com.snow.lib.enums.ResultEnum;
import com.snow.lib.exception.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

/**
 * Created by SNOW on 2018.01.18.
 */
public class ResultUtil {

    private ResultUtil() {
    }


    public static ResultVO success(Object data) {
        Body body = new Body("success", data, null);
        return new ResultVO(body);
    }

    public static ResultVO page(Page page) {
        Body body = new Body("success", page.getContent(), PageUtil.GetPaginationFrom(page));
        return new ResultVO(body);
    }

    public static ResultVO success() {
        return new ResultVO(HttpStatus.OK);
    }

    public static ResultVO unauthorized() {
        Body body = new Body("no authentication or expired jwt", null, null);
        return new ResultVO(body, HttpStatus.UNAUTHORIZED);
    }

    public static ResultVO error(BaseException exception) {
        Body body = new Body(exception.getMessage(), null, null);
        return new ResultVO(body, HttpStatus.valueOf(exception.getCode()));
    }

    public static ResultVO error(ResultEnum exceptionStatusEnum) {
        Body body = new Body(exceptionStatusEnum.getMsg(), null, null);
        return new ResultVO(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResultVO error(String message) {
        Body body = new Body(message, null, null);
        return new ResultVO(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
