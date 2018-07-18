package com.snow.blog.core.service.impl;

import com.snow.blog.core.repository.CategoryRepository;
import com.snow.blog.core.repository.entity.Category;
import com.snow.blog.core.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
