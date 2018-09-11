package com.snow.core.service.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "snow.storage")
public class StorageProperties {
    private String uploadDir = "/file";
}
