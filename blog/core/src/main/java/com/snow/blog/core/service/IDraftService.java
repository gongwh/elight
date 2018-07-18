package com.snow.blog.core.service;

import com.snow.blog.core.repository.entity.Draft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SNOW on 2018.01.31.
 */
public interface IDraftService {

    // 查
    List<Draft> getDraftsAll();

    Page<Draft> getDraftsAllByPage(Pageable pageable);

    Draft getNewestDraftByUserId(String userId);

    List<Draft> getDraftsByUserId(String userId);

    // 增，改
    Draft saveDraft(Draft Draft);

    // 删
    void deleteDraft(Draft Draft);
}
