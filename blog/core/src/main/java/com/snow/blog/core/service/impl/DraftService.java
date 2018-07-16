package com.snow.blog.core.service.impl;

import com.snow.blog.core.repository.DraftRepository;
import com.snow.blog.core.repository.entity.Draft;
import com.snow.blog.core.service.IDraftService;
import com.snow.blog.core.util.validator.CommonValidator;
import com.snow.blog.core.vo.DraftVO;
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
    public List<DraftVO> getDraftsAll() {
        List<Draft> drafts = draftRepository.findByEnableIsTrue();
        return BeanCopyUtil.createOnListCopy(drafts, DraftVO.class);
    }


    @Override
    public Page<DraftVO> getDraftsAllByPage(Pageable pageable) {
        Page<Draft> drafts = draftRepository.findByEnableIsTrue(pageable);
        return drafts.map(e -> BeanCopyUtil.createOnCopy(e, DraftVO.class));
    }

    @Override
    public DraftVO getNewestDraftByUserId(String userId) {
        Draft result = draftRepository.findFirstByUserIdAndEnableIsTrueOrderByUpdateTimeDesc(userId);
        if (null == result) {
            return null;
        }
        return BeanCopyUtil.createOnCopy(result, DraftVO.class);
    }

    @Override
    public List<DraftVO> getDraftsByUserId(String userId) {
        List<Draft> result = draftRepository.findByUserIdAndEnableIsTrueOrderByUpdateTimeDesc(userId);
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return BeanCopyUtil.createOnListCopy(result, DraftVO.class);
    }

    @Override
    public DraftVO saveDraft(DraftVO draftVO) {
        Draft draft = BeanCopyUtil.createOnCopy(draftVO, Draft.class);
        Draft result = draftRepository.save(draft);
        CommonValidator.saveOk(result);
        return BeanCopyUtil.createOnCopy(result, DraftVO.class);
    }

    @Override
    public void deleteDraft(DraftVO draftVO) {
        Draft draft = BeanCopyUtil.createOnCopy(draftVO, Draft.class);
        draft.setEnable(false);
        Draft result = draftRepository.save(draft);
        CommonValidator.delOk(result);
    }
}
