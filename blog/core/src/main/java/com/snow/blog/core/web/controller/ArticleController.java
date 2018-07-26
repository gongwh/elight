package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.util.validator.ArticleValidator;
import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import com.snow.security.core.repository.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by SNOW on 2018.01.25.
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articlesService;

    // 获取所有文章-分页
    @GetMapping("/page")
    public ResultVO getArticlePage(
            String userId,
            @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user) {
        if (StringUtils.isBlank(userId)){
            userId = user.getUserId();
        }
        Page<Article> page = articlesService.getArticlePage(userId, user.getUserId(), pageable);
        return ResultVOUtil.page(page);
    }

    // 获取所有文章-分页
    @GetMapping("/page/search")
    public ResultVO getArticleSearchPage(
            String userId,
            String content,
            @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user) {
        if (StringUtils.isBlank(userId)){
            userId = user.getUserId();
        }
        Page<Article> page = articlesService.getArticleSearchPage(userId, user.getUserId(), content, pageable);
        return ResultVOUtil.page(page);
    }



    // 获取指定文章
    @GetMapping("/{articleId}")
    public ResultVO getArticleByArticleId(@PathVariable("articleId") String articleId, @AuthenticationPrincipal User user) {
        Article article = articlesService.getArticleById(articleId, user.getUserId());
        return ResultVOUtil.success(article);
    }

    // 保存文章
    @PostMapping
    public ResultVO saveArticle(@RequestBody Article article, @AuthenticationPrincipal User user) {
        ArticleValidator.validate(article);
        if (null == article.getPersonal()) {
            article.setPersonal(false);
        }
        Article result = articlesService.saveArticle(article, user.getUserId());
        return ResultVOUtil.success(result);
    }

    // 删除文章
    @DeleteMapping
    public ResultVO deleteArticle(@RequestBody Article article, @AuthenticationPrincipal User user) {
        ArticleValidator.articleExist(article);
        articlesService.deleteArticle(article, user.getUserId());
        return ResultVOUtil.success();
    }

}
