package com.snow.blog.core.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
public class Article extends EntityBase {
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
    @Column(columnDefinition = "Text")
    private String contentHtml;
    // markdown 文本
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String contentMd;
    // html 文本
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String contentText;
    // 用户ID
    private String userId;
    // 是否私有
    private Boolean personal;

    // 文章标签
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "article_tag",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags = new HashSet<>();
    // 文章分类
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "article_category",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories;

}
