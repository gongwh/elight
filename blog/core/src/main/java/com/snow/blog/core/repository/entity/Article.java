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
    private String articleId;

    // 标题图片
    private String titleImgUrl;

    // 标题
    private String title;

    // html 文本缩略
    @Column
    private String contentTextSubNail;

    // 用户ID
    private String userId;

    // 是否私有
    private Boolean personal;

    // 文章标签
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "article_tag")
    private List<Tag> tags = new ArrayList<>();

    // 文章分类
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "article_category")
    private List<Category> categories = new ArrayList<>();

}
