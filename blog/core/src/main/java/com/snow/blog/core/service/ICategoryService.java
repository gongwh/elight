package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Category;

import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
public interface ICategoryService {
    List<Category> getAllCategory();
}
