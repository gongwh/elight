package com.snow.security.core.support;

import com.snow.security.core.validator.PasswordMatches;
import com.snow.security.core.validator.ValidEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @create by SNOW 2018.07.12
 */
@Data
@PasswordMatches
public class UserDto {

    private String firstName;

    private String lastName;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    private String password;

    private String matchingPassword;

    private String phoneNumber;
}
