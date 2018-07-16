package com.snow.security.core.service;

import com.snow.security.core.repository.UserRepository;
import com.snow.security.core.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @create by SNOW 2018.07.09
 */
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + email);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(),
                        user.getPassword(), enabled, accountNonExpired,
                        credentialsNonExpired, accountNonLocked,
                        getAuthorities(user.getAuthorities()));
    }

    public static Collection<GrantedAuthority> getAuthorities(List<String> authorityStr) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String auth : authorityStr) {
            authorities.add(new SimpleGrantedAuthority(auth));
        }
        return authorities;
    }

    public static String getAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        StringBuilder authorities = new StringBuilder();
        for (GrantedAuthority auth : grantedAuthorities) {
            authorities.append(auth.getAuthority()).append(",");
        }
        return authorities.toString();
    }
}
