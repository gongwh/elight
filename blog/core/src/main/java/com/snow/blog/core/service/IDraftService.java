package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Draft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SNOW on 2018.01.31.
 */
public interface IDraftService {

    // 分页查
    Page<Draft> getDraftPage(String userId,Pageable pageable);

    // 单个查
    Draft getNewestDraft(String userId);

    // 增，改
    Draft saveDraft(Draft Draft,String userId);

    // 删
    void deleteDraft(Draft Draft,String userId);
}
