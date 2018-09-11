package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.Draft;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.service.IDraftService;
import com.snow.blog.core.util.validator.ArticleValidator;
import com.snow.blog.core.util.validator.DraftValidator;
import com.snow.lib.BeanCopyUtil;
import com.snow.lib.result.ResultUtil;
import com.snow.lib.result.ResultVO;
import com.snow.security.core.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by SNOW on 2018.01.30.
 */
@RestController
@RequestMapping("/draft")
@Slf4j
public class DraftController {

    @Autowired
    private IDraftService draftService;

    @Autowired
    private IArticleService articleService;

    // 获取所有草稿-分页
    @GetMapping("/page")
    public ResultVO getDraftsAll(@PageableDefault(page = 0, size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                 @AuthenticationPrincipal User user) {
        Page<Draft> page = draftService.getDraftPage(user.getUserId(), pageable);
        return ResultUtil.page(page);
    }

    // 获取指定用户的最新草稿
    @GetMapping("/newest")
    public ResultVO getNewestDraftByUserId(@AuthenticationPrincipal User user) {
        Draft draft = draftService.getNewestDraft(user.getUserId());
        log.debug("加载最新草稿:{}", draft);
        return ResultUtil.success(draft);
    }

    @GetMapping("/article")
    public ResultVO loadDraftByArticleId(String articleId, @AuthenticationPrincipal User user) {
        Article article = articleService.getArticleById(articleId, user.getUserId());
        ArticleValidator.articleExist(article);
        Draft draftNew = new Draft();
        BeanCopyUtil.copy(article, draftNew);
        draftNew.setUpdateTime(null);
        Draft draftNewSaveResult = draftService.saveDraft(draftNew, user.getUserId());
        log.debug("加载新的文章为草稿,draftNew:{}", draftNewSaveResult.getArticleId());
        return ResultUtil.success(draftNewSaveResult);
    }

    // 保存草稿
    @PostMapping
    public ResultVO saveDraft(@RequestBody Draft draft, @AuthenticationPrincipal User user) {
        DraftValidator.validate(draft);
        Draft result = draftService.saveDraft(draft, user.getUserId());
        return ResultUtil.success(result);
    }

    // 删除草稿
    @DeleteMapping
    public ResultVO deleteDraft(@RequestBody Draft draft, @AuthenticationPrincipal User user) {
        DraftValidator.draftExist(draft);
        draftService.deleteDraft(draft, user.getUserId());
        log.debug("删除草稿,draft:{}", draft.getDraftId());
        return ResultUtil.success();
    }
}
