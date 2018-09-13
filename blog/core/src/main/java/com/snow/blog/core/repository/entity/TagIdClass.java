package com.snow.blog.core.repository.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagIdClass implements Serializable {
    private String userId;
    private String name;
}
