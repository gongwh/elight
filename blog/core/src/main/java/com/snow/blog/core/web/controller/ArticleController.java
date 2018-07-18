package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.util.validator.ArticleValidator;
import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by SNOW on 2018.01.25.
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articlesService;

    // 获取所有文章
    @GetMapping
    public ResultVO getArticlesAll() {
        List<Article> list = articlesService.getArticlesAll();
        return ResultVOUtil.success(list);
    }

    // 保存文章
    @PostMapping
    public ResultVO saveArticle(@RequestBody Article article, @Autowired Principal principal) {
        ArticleValidator.validate(article);
        if (StringUtils.isEmpty(article.getUserId())){
            article.setUserId(principal.getName());
        }
        Article result = articlesService.saveArticle(article);
        return ResultVOUtil.success(result);
    }

    // 删除文章
    @DeleteMapping
    public ResultVO deleteArticle(@RequestBody Article article) {
        ArticleValidator.articleExist(article);
        articlesService.deleteArticle(article);
        return ResultVOUtil.success();
    }

    // 获取指定文章
    @GetMapping("/{articleId}")
    public ResultVO getArticleByArticleId(@PathVariable("articleId") String articleId) {
        Article article = articlesService.getArticleByArticleId(articleId);
        return ResultVOUtil.success(article);
    }

    // 获取所有文章-分页
    @GetMapping("/page")
    public ResultVO getArticlesAllByPage(@PageableDefault(page = 0, size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Article> page = articlesService.getArticlesAllByPage(pageable);
        return ResultVOUtil.page(page);
    }

    // 获取用户所有文章
    @GetMapping("/user/{userId}")
    public ResultVO getArticlesByUserId(@PathVariable("userId") String userId) {
        List<Article> list = articlesService.getArticlesByUserId(userId);
        return ResultVOUtil.success(list);
    }

    // 获取用户所有文章-分页
    @GetMapping("/user/{userId}/page")
    public ResultVO getArticlesByUserIdAndPage(@PathVariable("userId") String userId, @PageableDefault(page = 0, size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Article> page = articlesService.getArticlesByUserIdAndPage(userId, pageable);
        return ResultVOUtil.page(page);
    }

}
