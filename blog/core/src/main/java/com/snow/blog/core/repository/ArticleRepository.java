package com.snow.blog.core.repository;

import com.snow.blog.core.repository.entity.Article;
import com.snow.core.util.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by SNOW on 2018.01.25.
 */
public interface ArticleRepository extends BaseRepository<Article> {

    /**
     * ID查询文章
     *
     * @param articleId
     * @return
     */
    Article findByArticleIdAndEnabledIsTrue(String articleId);

    /**
     * 根据用户查询文章
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndEnabledIsTrue(String userId, Pageable pageable);

    /**
     * 根据用户查询公共文章
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndPersonalIsFalseAndEnabledIsTrue(String userId, Pageable pageable);

    /**
     * 根据用户和标题查询文章
     *
     * @param userId
     * @param title
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndTitleLikeAndEnabledIsTrue(String userId, String title, Pageable pageable);

    /**
     * 根据用户和标题查询共有文章
     *
     * @param userId
     * @param title
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndTitleLikeAndPersonalIsFalseAndEnabledIsTrue(String userId, String title, Pageable pageable);

    /**
     * 根据用户和标签查询文章
     *
     * @param userId
     * @param tagNames
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndTags_NameInAndEnabledIsTrue(String userId, List<String> tagNames, Pageable pageable);

    /**
     * 根据用户和标签查询公共文章
     *
     * @param userId
     * @param tagNames
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndTags_NameInAndPersonalIsFalseAndEnabledIsTrue(String userId, List<String> tagNames, Pageable pageable);

    /**
     * 根据用户和标题以及标签查询文章
     *
     * @param userId
     * @param title
     * @param tagNames
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndTitleLikeAndTags_NameInAndEnabledIsTrue(String userId, String title, List<String> tagNames, Pageable pageable);

    /**
     * 根据用户和标题以及标签查询公共文章
     *
     * @param userId
     * @param title
     * @param tagNames
     * @param pageable
     * @return
     */
    Page<Article> findByUserIdAndTitleLikeAndTags_NameInAndPersonalIsFalseAndEnabledIsTrue(String userId, String title, List<String> tagNames, Pageable pageable);

}
