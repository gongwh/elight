package com.snow.blog.core.service.impl;

import com.snow.blog.core.repository.TagRepository;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.ITagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @create by SNOW 2018.07.18
 */
@Service
public class TagService implements ITagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getAllTag(String targetUserId, String currentUserId) {
        if (StringUtils.isEmpty(targetUserId)) {
            return tagRepository.findAllByEnabledIsTrue();
        }
        return tagRepository.findTagsByUserId(targetUserId);
    }

    @Override
    public Tag saveTag(Tag tag, String userId) {
        tag.setUserId(userId);
        tag.setEnabled(true);
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> saveTag(List<Tag> tags, String userId) {
        if (!CollectionUtils.isEmpty(tags)) {
            tags.forEach(tag -> {
                tag.setUserId(userId);
                tag.setEnabled(true);
            });
            Set tagSet = new HashSet<>(tags);
            List<Tag> tagTrimDup = new ArrayList<>(tagSet);
            return tagRepository.save(tagTrimDup);
        }
        return tags;
    }
}
