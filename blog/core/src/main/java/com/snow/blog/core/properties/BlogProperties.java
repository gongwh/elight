package com.snow.blog.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @create by SNOW 2018.07.25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "snow.blog")
public class BlogProperties {
    private ArticleProperties article = new ArticleProperties();
}
