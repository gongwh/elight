package com.snow.security.core.repository;

import com.snow.security.core.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @create by SNOW 2018.07.12
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
