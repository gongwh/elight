package com.snow.blog.core.repository.entity;

import com.snow.lib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @create by SNOW 2018.07.18
 */
@Data
@Entity
@Table(name = "category")
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Category extends EntityBase {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String categoryId;

    private String name;
}
