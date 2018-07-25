package com.snow.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "snow.security")
public class SecurityProperties {
    private PasswordOauthProperties password = new PasswordOauthProperties();
}
