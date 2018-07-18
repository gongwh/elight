package com.snow.blog.core.repository;

import com.snow.blog.core.repository.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @create by SNOW 2018.07.18
 */
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
