package com.snow.core.service.repository;

import com.snow.lib.repository.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class CreateAndUpdateService {

    @Before("execution(* com.snow.core.util.repository.BaseRepository.save(..)) && args(baseEntity)")
    public void doAfterGetArticleById(JoinPoint joinPoint, BaseEntity baseEntity) throws Throwable {
        // 处理完请求，返回内容
        // log.debug("[Entity] [保存监听] [baseEntity]", baseEntity);
        try {
            if (null != baseEntity) {
                baseEntity.setUpdateTime(new Date());
            }
        } catch (Exception e) {
            // log.debug("[Entity] [保存监听]", e);
        }
    }
}