package com.snow.blog.core.web.controller;

import com.snow.blog.core.service.ICategoryService;
import com.snow.lib.result.ResultUtil;
import com.snow.lib.result.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @create by SNOW 2018.07.18
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResultVO getAllCategory() {
        return ResultUtil.success(categoryService.getAllCategory());
    }
}
