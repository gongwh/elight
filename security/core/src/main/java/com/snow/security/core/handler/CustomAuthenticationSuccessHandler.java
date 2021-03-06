package com.snow.security.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snow.lib.result.Body;
import com.snow.lib.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @create by SNOW 2018.07.11
 */
@Service
@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("登陆成功 {}", ReflectionToStringBuilder.toString(authentication, ToStringStyle.MULTI_LINE_STYLE));
        if (MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())
                || MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(new Body(authentication)));
            response.getWriter().flush();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
