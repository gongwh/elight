package com.snow.security.core.service;

import com.snow.security.core.exception.EmailExistsException;
import com.snow.security.core.repository.UserRepository;
import com.snow.security.core.repository.entity.User;
import com.snow.security.core.support.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @create by SNOW 2018.07.09
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUserAccount(UserDto userDto) {
        if (emailExist(userDto.getEmail())) {
            throw new EmailExistsException(-1,
                    "There is an account with that email address:"  + userDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setAuthorities(Arrays.asList("USER"));
        user.setPhoneNumber(userDto.getPhoneNumber());
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
