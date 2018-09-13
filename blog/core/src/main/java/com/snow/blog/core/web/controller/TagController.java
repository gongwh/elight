package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.TagRepository;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.ITagService;
import com.snow.lib.result.ResultUtil;
import com.snow.lib.result.ResultVO;
import com.snow.security.core.repository.entity.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
@RestController
@RequestMapping("/tag")
@Validated
public class TagController {

    @Autowired
    private ITagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public ResultVO getAllTag(@NotEmpty String userId, @AuthenticationPrincipal User user) {
        List<Tag> result = tagService.getAllTag(userId, user == null ? null : user.getUserId());
        return ResultUtil.success(result);
    }

    @PostMapping
    public ResultVO saveTag(@RequestBody Tag tag, @AuthenticationPrincipal User user) {
        if (null == user) {
            return ResultUtil.unauthorized();
        }
        Tag result = tagService.saveTag(tag, user.getUserId());
        return ResultUtil.success(result);
    }

    @DeleteMapping
    public ResultVO deleteTag(@RequestBody Tag tag, @AuthenticationPrincipal User user) {
        if (null == user) {
            return ResultUtil.unauthorized();
        }
        tag.setUserId(user.getUserId());
        tagRepository.delete(tag);
        return ResultUtil.success();
    }
}
