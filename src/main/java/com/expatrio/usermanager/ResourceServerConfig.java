package com.expatrio.usermanager;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@Order(100)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        http
                .cors()
                .and()
                .requestMatchers()
                .antMatchers("/api/**", "/oauth/userinfo/**")
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/api/**"
//                ).authenticated()
//        .and().authorizeRequests().antMatchers("/error").permitAll();
    }
}
