package com.snow.security.core.service;

import com.snow.security.core.repository.UserRepository;
import com.snow.security.core.repository.entity.Authority;
import com.snow.security.core.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @create by SNOW 2018.07.09
 */
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + email);
        }
        return user;
    }

    public static String getAuthorities(Collection<Authority> grantedAuthorities) {
        StringBuilder authorities = new StringBuilder();
        for (Authority auth : grantedAuthorities) {
            authorities.append(auth.getAuthority()).append(",");
        }
        return authorities.toString();
    }
}
