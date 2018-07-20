package com.snow.blog.core.repository.entity;

import com.snow.lib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 草稿
 * Created by SNOW on 2018.01.18.
 */
@Data
@Entity
@Table(name = "draft")
@DynamicUpdate
@DynamicInsert
@EqualsAndHashCode(callSuper = true)
public class Draft extends EntityBase {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String draftId;
    // 文章ID
    private String articleId;
    // 用户ID
    private String userId;
    // 标题图片
    private String titleImgUrl;
    // 标题
    private String title;
    // markdown 文本
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String contentMd;
}
