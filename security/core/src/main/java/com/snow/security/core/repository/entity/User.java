package com.snow.security.core.repository.entity;

import com.snow.lib.repository.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @create by SNOW 2018.07.12
 */
@DynamicUpdate
@DynamicInsert
@Entity
@NoArgsConstructor
@Data
public class User extends BaseEntity implements UserDetails, CredentialsContainer {

    public User(String username, String password, Collection<Authority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public User(String username, String password, boolean enabled,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked, Collection<Authority> authorities) {
        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = new HashSet<>(authorities);
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String userId;
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

    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
