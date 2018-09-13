package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * @create by SNOW 2018.07.18
 */
public interface ITagService {

    List<Tag> getAllTag(String targetUserId, String currentUserId);

    Tag saveTag(Tag tag, String userId);

    List<Tag> saveTag(List<Tag> tags, String userId);
}
