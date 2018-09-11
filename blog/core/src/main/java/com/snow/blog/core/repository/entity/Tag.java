package com.snow.blog.core.repository.entity;

import com.snow.lib.repository.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @create by SNOW 2018.07.18
 */
@Data
@Entity
@Table(name = "tag")
@DynamicUpdate
@DynamicInsert
@ToString(callSuper = true)
@IdClass(TagIdClass.class)
public class Tag extends BaseEntity implements Serializable {

    @Id
    private String userId;

    @Id
    private String name;

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Tag tag = (Tag) obj;
        return StringUtils.equals(this.getUserId(), tag.getUserId()) && StringUtils.equals(this.getName(), tag.getName());
    }
}