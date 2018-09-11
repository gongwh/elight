package com.snow.blog.core.repository;

import com.snow.blog.core.repository.entity.Tag;
import com.snow.core.util.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
public interface TagRepository extends BaseRepository<Tag>{
    List<Tag> findAllByEnabledIsTrue();
    List<Tag> findTagsByUserId(String userId);
}
