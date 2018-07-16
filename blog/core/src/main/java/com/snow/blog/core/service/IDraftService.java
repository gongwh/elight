package com.snow.blog.core.service;

import com.snow.blog.core.vo.DraftVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by SNOW on 2018.01.31.
 */
public interface IDraftService {

    // 查
    List<DraftVO> getDraftsAll();

    Page<DraftVO> getDraftsAllByPage(Pageable pageable);

    DraftVO getNewestDraftByUserId(String userId);

    List<DraftVO> getDraftsByUserId(String userId);

    // 增，改
    DraftVO saveDraft(DraftVO DraftVO);

    // 删
    void deleteDraft(DraftVO DraftVO);
}
