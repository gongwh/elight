package com.snow.security.core.repository.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable( name = "user_authority",
            joinColumns =        { @JoinColumn(name = "user_id")  },
            inverseJoinColumns = { @JoinColumn(name = "authority_number")}
    )
    private Set<Authority> authorities;
    private boolean enable;
}
