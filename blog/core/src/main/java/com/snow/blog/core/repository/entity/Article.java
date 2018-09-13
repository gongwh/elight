package com.snow.blog.core.repository.entity;

import com.snow.lib.repository.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章
 * Created by SNOW on 2018.01.18.
 */
@Data
@Entity
@Table(name = "article")
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String articleId;

    // 标题图片
    private String titleImgUrl;

    // 标题
    private String title;

    // html 文本
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "mediumtext")
    private String contentHtml;

    // markdown 文本
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "mediumtext")
    private String contentMd;

    // html 文本
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "mediumtext")
    private String contentText;

    // html 文本缩略
    @Column
    private String contentTextSubNail;

    // 用户ID
    private String userId;

    // 是否私有
    private Boolean personal;

    // 文章标签
    @ManyToMany
    @JoinTable(name = "article_tag")
    private List<Tag> tags = new ArrayList<>();

    // 文章分类
    @ManyToMany
    @JoinTable(name = "article_category")
    private List<Category> categories = new ArrayList<>();

}
