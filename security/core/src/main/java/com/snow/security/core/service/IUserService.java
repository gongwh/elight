package com.snow.security.core.service;

import com.snow.security.core.exception.EmailExistsException;
import com.snow.security.core.repository.entity.User;
import com.snow.security.core.support.UserDto;

/**
 * @create by SNOW 2018.07.12
 */
public interface IUserService {
    User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;
}
