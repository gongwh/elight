package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.util.validator.ArticleValidator;
import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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
            @Autowired Principal principal) {
        Page<Article> page = articlesService.getArticlePage(userId, principal.getName(), pageable);
        return ResultVOUtil.page(page);
    }

    // 获取指定文章
    @GetMapping("/{articleId}")
    public ResultVO getArticleByArticleId(@PathVariable("articleId") String articleId, @Autowired Authentication principal) {
        Article article = articlesService.getArticleById(articleId, principal.getName());
        return ResultVOUtil.success(article);
    }

    // 保存文章
    @PostMapping
    public ResultVO saveArticle(@RequestBody Article article, @Autowired Principal principal) {
        ArticleValidator.validate(article);
        if (null == article.getPersonal()) {
            article.setPersonal(false);
        }
        Article result = articlesService.saveArticle(article, principal.getName());
        return ResultVOUtil.success(result);
    }

    // 删除文章
    @DeleteMapping
    public ResultVO deleteArticle(@RequestBody Article article, @Autowired Principal principal) {
        ArticleValidator.articleExist(article);
        articlesService.deleteArticle(article, principal.getName());
        return ResultVOUtil.success();
    }

}
