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

import java.util.List;

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

    // 获取所有草稿
    @GetMapping
    public ResultVO getDraftsAll() {
        List<Draft> list = draftService.getDraftsAll();
        return ResultVOUtil.success(list);
    }

    // 获取所有草稿-分页
    @GetMapping("/page")
    public ResultVO getDraftsAll(@PageableDefault(page = 0, size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Draft> page = draftService.getDraftsAllByPage(pageable);
        return ResultVOUtil.page(page);
    }

    // 获取指定用户的最新草稿
    @GetMapping("/newest")
    public ResultVO getNewestDraftByUserId(String userId) {
        Draft draft = draftService.getNewestDraftByUserId(userId);
        log.debug("加载最新草稿:{}", draft);
        return ResultVOUtil.success(draft);
    }

    @GetMapping("/article")
    public ResultVO loadDraftByArticleId(String userId, String articleId) {
        Article article = articleService.getArticleByArticleId(articleId);
        ArticleValidator.articleExist(article);
        Draft draftNew = new Draft();
        BeanCopyUtil.copy(article, draftNew);
        draftNew.setUpdateTime(null);
        Draft draftNewSaveResult = draftService.saveDraft(draftNew);
        log.debug("加载新的文章为草稿,draftNew:{}", draftNewSaveResult.getArticleId());
        return ResultVOUtil.success(draftNewSaveResult);
    }

    @GetMapping("/{userId}")
    public ResultVO getDraftsByUserId(@PathVariable("userId") String userId) {
        List<Draft> drafts = draftService.getDraftsByUserId(userId);
        return ResultVOUtil.success(drafts);
    }

    // 保存草稿
    @PostMapping
    public ResultVO saveDraft(@RequestBody Draft draft) {
        DraftValidator.validate(draft);
        Draft result = draftService.saveDraft(draft);
        return ResultVOUtil.success(result);
    }

    // 删除草稿
    @DeleteMapping
    public ResultVO deleteDraft(@RequestBody Draft draft) {
        DraftValidator.draftExist(draft);
        draftService.deleteDraft(draft);
        log.debug("删除草稿,draft:{}", draft.getDraftId());
        return ResultVOUtil.success();
    }
}
