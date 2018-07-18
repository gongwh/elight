package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Tag;

import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
public interface ITagService {
    List<Tag> getAllTag(String userId);
}
