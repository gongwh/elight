package com.snow.security.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snow.security.core.repository.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ObjectMapper objectMapper;

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
            String token = request.getHeader("Authorization");

            Claims claims = Jwts.parser()
                    .setSigningKey("SNOW")
                    .parseClaimsJws(token.replace("Bearer ", "")).getBody();
            String userStr = claims.getSubject();
            User user = objectMapper.readValue(userStr,User.class);
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
        } catch (AuthenticationException |JwtException failed) {

            SecurityContextHolder.clearContext();

            log.error("Authentication request for failed: " + failed);

            // onUnsuccessfulAuthentication(request, response, failed);

            chain.doFilter(request, response);

            return;
        }

        chain.doFilter(request, response);
    }
}
