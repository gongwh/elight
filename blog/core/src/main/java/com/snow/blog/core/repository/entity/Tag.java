package com.snow.blog.core.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
@Data
@Entity
@Table(name = "tag")
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tag extends EntityBase {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String tagId;

    private String userId;

    private String name;
}