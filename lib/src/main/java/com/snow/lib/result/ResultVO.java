package com.snow.lib.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by SNOW on 2018.01.18.
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 5216059160426137256L;

    // 时间
    private Date timestamp;

    // 错误码
    private Integer status;


    // 提示信息
    private String message;

    // 具体内容
    private T data;

    // 分页信息
    private Pagination pagination;

    // 异常原因
    private String error;

    // 异常类名
    private String exception;

    // 请求地址
    private String path;

    public ResultVO() {
    }

    public ResultVO(Integer status, String message, T data, Pagination pagination) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
        this.timestamp = new Date();
    }
}
