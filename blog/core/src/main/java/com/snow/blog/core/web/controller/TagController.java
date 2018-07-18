package com.snow.blog.core.web.controller;

import com.snow.blog.core.repository.entity.Tag;
import com.snow.blog.core.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @create by SNOW 2018.07.18
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private ITagService tagService;

    @GetMapping
    public List<Tag> getAllTag(@Autowired Principal principal){
        return tagService.getAllTag(principal.getName());
    }
}
