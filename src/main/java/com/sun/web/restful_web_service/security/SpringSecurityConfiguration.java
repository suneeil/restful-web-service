package com.sun.web.restful_web_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
public class SpringSecurityConfiguration {

    @Bean 
    public SecurityFilterChain fiterChain(HttpSecurity http) {
        //1) All requests should be authenticated
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        //2) Authenticate requests using HTTP Basic credentials
        http.httpBasic(withDefaults());
        //3) Require a CSRF token for state-changing requests such as POST, PUT, and DELETE
        http.csrf(csrf -> csrf.disable());
        
        return http.build();
    }
}
