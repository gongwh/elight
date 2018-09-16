package com.snow.core.util.repository;


import com.snow.lib.repository.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, String> {

    @Modifying
    @Query("update #{#entityName} set enabled = false where id = ?1")
    void delete(String id);

    @Modifying
    @Query("update #{#entityName} set enabled = false where id IN ?1")
    void delete(List<String> ids);
}
