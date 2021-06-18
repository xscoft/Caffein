package com.caffein.common.security;

import com.caffein.service.MemberService;
import com.caffein.service.MemberServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberServiceImpl memberServiceImpl;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/*/register", "/*/write").hasAnyRole("ADMIN","MEMBER","CAFE_OWNER")
                .antMatchers("/admin/**").hasRole("ADMIN");

        http.formLogin();
        http.csrf().disable();
        http.logout();
        http.rememberMe().tokenValiditySeconds(60 * 60 * 7).userDetailsService(memberServiceImpl);
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }
     */
}
