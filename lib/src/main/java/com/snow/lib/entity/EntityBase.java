package com.snow.lib.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by SNOW on 2018.01.25.
 */
@Data
@MappedSuperclass
public class EntityBase {
    protected Boolean enabled = true;
    @CreatedDate
    protected Date createTime = new Date();
    @LastModifiedDate
    protected Date updateTime = new Date();
}
