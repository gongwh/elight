package com.snow.blog.core.repository;

import com.snow.blog.core.repository.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
public interface TagRepository extends JpaRepository<Tag,Integer> {
    List<Tag> findTagsByUserId(String userId);
    List<Tag> findByNameAndUserId(String name,String userId);
}
