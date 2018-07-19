package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Article;
import com.snow.blog.core.repository.entity.Draft;
import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.service.IDraftService;
import com.snow.blog.core.util.validator.ArticleValidator;
import com.snow.blog.core.util.validator.DraftValidator;
import com.snow.lib.BeanCopyUtil;
import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
                                 @Autowired Principal principal) {
        Page<Draft> page = draftService.getDraftPage(principal.getName(), pageable);
        return ResultVOUtil.page(page);
    }

    // 获取指定用户的最新草稿
    @GetMapping("/newest")
    public ResultVO getNewestDraftByUserId(@Autowired Principal principal) {
        Draft draft = draftService.getNewestDraft(principal.getName());
        log.debug("加载最新草稿:{}", draft);
        return ResultVOUtil.success(draft);
    }

    @GetMapping("/article")
    public ResultVO loadDraftByArticleId(String articleId, @Autowired Principal principal) {
        Article article = articleService.getArticleById(articleId, principal.getName());
        ArticleValidator.articleExist(article);
        Draft draftNew = new Draft();
        BeanCopyUtil.copy(article, draftNew);
        draftNew.setUpdateTime(null);
        Draft draftNewSaveResult = draftService.saveDraft(draftNew, principal.getName());
        log.debug("加载新的文章为草稿,draftNew:{}", draftNewSaveResult.getArticleId());
        return ResultVOUtil.success(draftNewSaveResult);
    }

    // 保存草稿
    @PostMapping
    public ResultVO saveDraft(@RequestBody Draft draft, @Autowired Principal principal) {
        DraftValidator.validate(draft);
        Draft result = draftService.saveDraft(draft, principal.getName());
        return ResultVOUtil.success(result);
    }

    // 删除草稿
    @DeleteMapping
    public ResultVO deleteDraft(@RequestBody Draft draft, @Autowired Principal principal) {
        DraftValidator.draftExist(draft);
        draftService.deleteDraft(draft, principal.getName());
        log.debug("删除草稿,draft:{}", draft.getDraftId());
        return ResultVOUtil.success();
    }
}
