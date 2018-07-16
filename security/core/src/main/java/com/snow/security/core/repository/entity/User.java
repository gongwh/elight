package com.snow.security.core.repository.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

/**
 * @create by SNOW 2018.07.12
 */
@Entity
@Data
public class User {

    public User() {
        super();
        this.enable = false;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    @ElementCollection(fetch = EAGER)
    private List<String> authorities;
    private boolean enable;
}
