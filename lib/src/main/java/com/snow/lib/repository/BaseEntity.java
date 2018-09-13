package com.snow.lib.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class BaseEntity {
    @JsonIgnore
    protected Boolean enabled = true;
    @CreatedDate
    Date createTime = new Date();
    @LastModifiedDate
    Date updateTime = new Date();
}
