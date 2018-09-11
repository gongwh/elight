package com.snow.blog.core.web.controller.support;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SearchArticleCondition {
    @NotNull
    String userId;
    String title;
    List<String> tagNames;
}
