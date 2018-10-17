package com.snow.lib.result;

import lombok.Data;

import java.util.Date;

@Data
public class BaseVO {
    protected Boolean enabled = true;
    protected Date createTime = new Date();
    protected Date updateTime = new Date();
}
