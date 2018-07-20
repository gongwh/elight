package com.snow.security.core.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snow.lib.result.ResultVOUtil;
import com.snow.security.core.repository.entity.User;
import com.snow.security.core.service.CustomUserDetailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * @create by SNOW 2018.07.11
 */
public class JwtJsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())
                || MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {

            String username = null;
            String password = null;
            try (InputStream is = request.getInputStream()) {
                Map<String, String> loginInfo = objectMapper.readValue(is, new TypeReference<Map<String, String>>() {
                });
                username = loginInfo.get("username");
                password = loginInfo.get("password");
            } catch (IOException e) {
                // do nothing
            }
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);

            setDetails(request, authRequest);

            Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);
            // === 这一块本身在AuthenticationSuccessHandler中做处理的，但是配置成功处理器不成功。
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            try {
                response.getWriter().write(objectMapper.writeValueAsString(ResultVOUtil.success((User) authentication.getPrincipal())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // ===
            return authentication;
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(objectMapper.writeValueAsString((User) authResult.getPrincipal()))
                .claim("authorities", getAuthoritiesToStr(authResult))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "SNOW")
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }

    private String getAuthoritiesToStr(Authentication authResult) throws JsonProcessingException {
        return CustomUserDetailService.getAuthorities(((User) authResult.getPrincipal()).getAuthorities());
    }
}
