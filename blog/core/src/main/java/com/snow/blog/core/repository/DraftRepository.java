package com.snow.blog.core.repository;

import com.snow.blog.core.repository.entity.Draft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by SNOW on 2018.01.31.
 */
public interface DraftRepository extends JpaRepository<Draft, String> {

    Page<Draft> findByUserIdAndEnableIsTrue(String userId,Pageable pageable);

    Draft findByDraftIdAndUserIdAndEnableIsTrue(String draftId,String userId);

    Draft findFirstByUserIdAndEnableIsTrueOrderByUpdateTimeDesc(String userId);

}
