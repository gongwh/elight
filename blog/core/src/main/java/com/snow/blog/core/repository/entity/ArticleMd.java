package com.snow.blog.core.repository.entity;

import com.snow.lib.repository.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "article_md")
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArticleMd extends BaseEntity {

    @Id
    private String articleId;

    // markdown 文本
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "mediumtext")
    private String contentMd;

}