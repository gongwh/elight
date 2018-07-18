package com.snow.blog.core.service.impl;

import com.snow.blog.core.repository.DraftRepository;
import com.snow.blog.core.repository.entity.Draft;
import com.snow.blog.core.service.IDraftService;
import com.snow.blog.core.util.validator.CommonValidator;
import com.snow.lib.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by SNOW on 2018.01.31.
 */
@Service
public class DraftService implements IDraftService {

    @Autowired
    private DraftRepository draftRepository;

    @Override
    public List<Draft> getDraftsAll() {
        List<Draft> drafts = draftRepository.findByEnableIsTrue();
        return drafts;
    }


    @Override
    public Page<Draft> getDraftsAllByPage(Pageable pageable) {
        Page<Draft> drafts = draftRepository.findByEnableIsTrue(pageable);
        return drafts.map(e -> BeanCopyUtil.createOnCopy(e, Draft.class));
    }

    @Override
    public Draft getNewestDraftByUserId(String userId) {
        Draft result = draftRepository.findFirstByUserIdAndEnableIsTrueOrderByUpdateTimeDesc(userId);
        if (null == result) {
            return null;
        }
        return result;
    }

    @Override
    public List<Draft> getDraftsByUserId(String userId) {
        List<Draft> result = draftRepository.findByUserIdAndEnableIsTrueOrderByUpdateTimeDesc(userId);
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result;
    }

    @Override
    public Draft saveDraft(Draft draft) {
        Draft result = draftRepository.save(draft);
        CommonValidator.saveOk(result);
        return result;
    }

    @Override
    public void deleteDraft(Draft draft) {
        draft.setEnable(false);
        Draft result = draftRepository.save(draft);
        CommonValidator.delOk(result);
    }
}
