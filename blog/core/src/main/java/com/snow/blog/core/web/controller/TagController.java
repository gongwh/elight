package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.ITagService;
import com.snow.lib.enums.ResultEnum;
import com.snow.lib.result.ResultVO;
import com.snow.lib.result.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResultVO getAllTag(@Autowired Principal principal) {
        List<Tag> result = tagService.getAllTag(principal.getName());
        return ResultVOUtil.success(result);
    }

    @PostMapping
    public ResultVO saveTag(@RequestBody Tag tag, @Autowired Principal principal) {
        Tag result = tagService.saveTag(tag, principal.getName());
        if (null != result) {
            return ResultVOUtil.success(result);
        } else {
            return ResultVOUtil.error(ResultEnum.SAVE_ERROR.getCode(), "该标签已存在");
        }
    }
}
