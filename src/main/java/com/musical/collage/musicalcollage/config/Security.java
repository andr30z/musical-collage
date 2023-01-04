package com.musical.collage.musicalcollage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web ->
      web
        .ignoring()
        // Spring Security should completely ignore URLs starting with /resources/
        .requestMatchers("/resources/**");
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
    return http.build();
  }
}
