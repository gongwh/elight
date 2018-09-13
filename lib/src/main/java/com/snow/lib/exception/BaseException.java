package com.snow.lib.exception;

import com.snow.lib.enums.ResultEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by SNOW on 2018.01.18.
 */
@Data
public class BaseException extends RuntimeException {

    private Integer code;

    public BaseException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public BaseException(HttpStatus status) {
        this(status.getReasonPhrase(), status.value());
    }

    public BaseException(ResultEnum statusEnum) {
        this(statusEnum.getMsg(), statusEnum.getCode());
    }


    public BaseException(String msg) {
        this(msg, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
