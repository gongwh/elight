package com.snow.lib.result;

import lombok.Data;

import java.util.Date;

@Data
public class Body {

    // 时间
    private Date timestamp = new Date();

    // 提示信息
    private String message;

    // 具体内容
    private Object data;

    // 分页信息
    private Pagination pagination;

    public Body(Object data) {
        this("success",data, null);
    }

    public Body(String message, Object data, Pagination pagination) {
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }
}