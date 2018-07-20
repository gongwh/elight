package com.snow.security.core.repository.entity;

import com.snow.lib.entity.EntityBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @create by SNOW 2018.07.18
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
public class Authority extends EntityBase implements GrantedAuthority {

    public Authority(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Integer number;

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
