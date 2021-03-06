package com.snow.security.core.config;

import com.snow.security.core.filter.JwtAuthenticationFilter;
import com.snow.security.core.filter.JwtJsonUsernamePasswordAuthenticationFilter;
import com.snow.security.core.filter.RestAuthenticationEntryPoint;
import com.snow.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @create by SNOW 2018.07.09
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({SecurityProperties.class})
public class CoreConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public JwtJsonUsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        JwtJsonUsernamePasswordAuthenticationFilter authenticationFilter = new JwtJsonUsernamePasswordAuthenticationFilter();
        authenticationFilter.setFilterProcessesUrl("/auth/login");
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean());
        return jwtAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 授权配置
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/auth/**", "/user/registration","/file/**").permitAll()
                .antMatchers(securityProperties.getPermitAnts()).permitAll()
                .anyRequest().authenticated();

        // 表单认证
        http.formLogin();

        // 跨站请求伪造,跨域
        http.csrf().disable().cors();

        // JSON+JWT认证流程,
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);

        // 没有认证时
        http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());

        // 登出
        http.logout().logoutUrl("/auth/logout").deleteCookies("JSESSIONID");
        // 认证通过，权限不足时
        // http.accessDenied()...
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
