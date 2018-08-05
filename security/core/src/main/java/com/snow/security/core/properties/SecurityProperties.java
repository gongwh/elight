package com.snow.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "snow.security")
public class SecurityProperties {
    private String[] permitAnts = {};
    private PasswordOauthProperties password = new PasswordOauthProperties();
}
