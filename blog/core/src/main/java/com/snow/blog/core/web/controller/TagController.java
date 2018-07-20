package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.ITagService;
import com.snow.lib.enums.ResultEnum;
import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import com.snow.security.core.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private ITagService tagService;

    @GetMapping
    public ResultVO getAllTag(@AuthenticationPrincipal User user) {
        List<Tag> result = tagService.getAllTag(user.getUserId());
        return ResultVOUtil.success(result);
    }

    @PostMapping
    public ResultVO saveTag(@RequestBody Tag tag, @AuthenticationPrincipal User user) {
        Tag result = tagService.saveTag(tag, user.getUserId());
        if (null != result) {
            return ResultVOUtil.success(result);
        } else {
            return ResultVOUtil.error(ResultEnum.SAVE_ERROR.getCode(), "该标签已存在");
        }
    }
}
