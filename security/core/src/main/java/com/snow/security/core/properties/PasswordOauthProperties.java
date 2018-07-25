package com.snow.security.core.properties;

import lombok.Data;

@Data
public class PasswordOauthProperties {
    private int jwtExpirationHours = 24;
}
