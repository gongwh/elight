package com.snow.security.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9]+([a-zA-Z0-9_\u4e00-\u9fa5])*[a-zA-Z0-9\u4e00-\u9fa5]+$";

    @Override
    public void initialize(ValidUsername validUsername) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
