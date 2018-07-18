package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Category;
import com.snow.blog.core.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }
}
