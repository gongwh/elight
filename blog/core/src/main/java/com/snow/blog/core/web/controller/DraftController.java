package com.snow.blog.core.web.controller;

import com.snow.blog.core.service.IArticleService;
import com.snow.blog.core.service.IDraftService;
import com.snow.blog.core.util.validator.ArticleValidator;
import com.snow.blog.core.util.validator.DraftValidator;
import com.snow.blog.core.vo.ArticleVO;
import com.snow.blog.core.vo.DraftVO;
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
        List<DraftVO> list = draftService.getDraftsAll();
        return ResultVOUtil.success(list);
    }

    // 获取所有草稿-分页
    @GetMapping("/page")
    public ResultVO getDraftsAll(@PageableDefault(page = 0, size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DraftVO> page = draftService.getDraftsAllByPage(pageable);
        return ResultVOUtil.page(page);
    }

    // 获取指定用户的最新草稿
    @GetMapping("/newest")
    public ResultVO getNewestDraftByUserId(String userId) {
        DraftVO draftVO = draftService.getNewestDraftByUserId(userId);
        log.debug("加载最新草稿:{}",draftVO);
        return ResultVOUtil.success(draftVO);
    }

    @GetMapping("/article")
    public ResultVO loadDraftByArticleId(String userId, String articleId) {
        ArticleVO articleVO = articleService.getArticleByArticleId(articleId);
        ArticleValidator.articleExist(articleVO);
        DraftVO draftNew = new DraftVO();
        BeanCopyUtil.copy(articleVO,draftNew);
        draftNew.setUpdateTime(null);
        DraftVO draftNewSaveResult = draftService.saveDraft(draftNew);
        log.debug("加载新的文章为草稿,draftNew:{}",draftNewSaveResult.getArticleId());
        return ResultVOUtil.success(draftNewSaveResult);
    }

    @GetMapping("/{userId}")
    public ResultVO getDraftsByUserId(@PathVariable("userId") String userId) {
        List<DraftVO> drafts = draftService.getDraftsByUserId(userId);
        return ResultVOUtil.success(drafts);
    }

    // 保存草稿
    @PostMapping
    public ResultVO saveDraft(@RequestBody DraftVO draftVO) {
        DraftValidator.validate(draftVO);
        DraftVO result = draftService.saveDraft(draftVO);
        return ResultVOUtil.success(result);
    }

    // 删除草稿
    @DeleteMapping
    public ResultVO deleteDraft(@RequestBody DraftVO draftVO) {
        DraftValidator.draftExist(draftVO);
        draftService.deleteDraft(draftVO);
        log.debug("删除草稿,draft:{}", draftVO.getDraftId());
        return ResultVOUtil.success();
    }
}
