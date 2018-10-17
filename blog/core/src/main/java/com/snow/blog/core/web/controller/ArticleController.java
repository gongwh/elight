package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.util.validator.ArticleValidator;
import com.snow.blog.core.vo.ArticleVO;
import com.snow.blog.core.web.controller.support.SearchArticleCondition;
import com.snow.lib.result.ResultUtil;
import com.snow.lib.result.ResultVO;
import com.snow.security.core.repository.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created by SNOW on 2018.01.25.
 */
@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private IArticleService articlesService;

    // 获取所有文章-分页
    @GetMapping("/page")
    public ResultVO getArticlesPage(
            @NotEmpty String userId,
            @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user) {
        Page<Article> page = articlesService.getArticlePage(userId, user == null ? null : user.getUserId(), pageable);
        return ResultUtil.page(page);
    }

    // 搜索所有文章-分页
    @PostMapping("/page/search")
    public ResultVO searchArticles(
            @RequestBody SearchArticleCondition condition,
            @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user) {
        Page page = articlesService.searchArticleByCondition(condition, null == user ? null : user.getUserId(), pageable);
        return ResultUtil.page(page);
    }

    // 获取指定文章
    @GetMapping("/{articleId}")
    public ResultVO getArticleByArticleId(@NotNull @PathVariable("articleId") String articleId, @AuthenticationPrincipal User user) {
        ArticleVO articleVO = articlesService.getArticleById(articleId, user == null ? null : user.getUserId());
        return ResultUtil.success(articleVO);
    }

    // 保存文章
    @PostMapping
    public ResultVO saveArticle(@RequestBody ArticleVO articleVO, @AuthenticationPrincipal User user) {
        if (null == user) {
            return ResultUtil.unauthorized();
        }
        ArticleValidator.validate(articleVO);
        if (null == articleVO.getPersonal()) {
            articleVO.setPersonal(false);
        }
        ArticleVO result = articlesService.saveArticle(articleVO, user == null ? null : user.getUserId());
        return ResultUtil.success(result);
    }

    // 删除文章
    @DeleteMapping
    public ResultVO deleteArticle(@RequestBody ArticleVO articleVO, @AuthenticationPrincipal User user) {
        if (null == user) {
            return ResultUtil.unauthorized();
        }
        Assert.isTrue(StringUtils.equals(user.getUserId(), articleVO.getUserId()), "操作受限");
        ArticleValidator.articleExist(articleVO);
        articlesService.deleteArticle(articleVO);
        return ResultUtil.success();
    }

}
