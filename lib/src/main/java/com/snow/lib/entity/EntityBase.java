package com.snow.lib.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by SNOW on 2018.01.25.
 */
@Data
@MappedSuperclass
public class EntityBase {
    protected Boolean enabled = true;
    protected Date createTime = new Date();
    protected Date updateTime = new Date();
}
