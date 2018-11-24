package com.snow.security.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snow.lib.result.ResultUtil;
import com.snow.security.core.properties.SecurityProperties;
import com.snow.security.core.repository.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @create by SNOW 2018.07.16
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    private RememberMeServices rememberMeServices = new NullRememberMeServices();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = header;

            Claims claims = Jwts.parser()
                    .setSigningKey(securityProperties.getPasswordOauth().getJwtSigningKey())
                    .parseClaimsJws(token.replace("Bearer ", "")).getBody();
            String userStr = claims.getSubject();
            User user = objectMapper.readValue(userStr, User.class);
            String authorities = (String) claims.get("authorities");
            if (null == user) {
                chain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

            log.debug("Authentication success: " + authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            onSuccessfulAuthentication(request, response, authenticationToken);
        } catch (AuthenticationException | JwtException failed) {

            SecurityContextHolder.clearContext();
            logger.error("Authentication request for failed: " + failed.getMessage());
            this.rememberMeServices.loginFail(request, response);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.unauthorized().getBody()));
            return;
        }

        chain.doFilter(request, response);
    }
}
