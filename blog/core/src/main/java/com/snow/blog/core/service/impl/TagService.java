package com.snow.blog.core.service.impl;

import com.snow.blog.core.repository.TagRepository;
import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
@Service
public class TagService implements ITagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getAllTag(String userId) {
        return tagRepository.findTagsByUserId(userId);
    }

    @Override
    public Tag saveTag(Tag tag, String userId) {
        tag.setUserId(userId);
        List<Tag> exist = tagRepository.findByNameAndUserId(tag.getName(),userId);
        if (!CollectionUtils.isEmpty(exist)){
            return tagRepository.save(tag);
        }else {
            return null;
        }
    }
}
