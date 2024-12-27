package com.authenticated.api.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> {
          try {
            auth
                .requestMatchers("/v*/public/**", "/swagger-ui/**", "/v3/api-docs/**")
                .permitAll() // allow access to /public/** and swagger endpoints
                .anyRequest().authenticated() // require authentication for all other endpoints
                .and()
                .oauth2ResourceServer()
                .jwt(); // validates JWT with the configured public key
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
    );

    return http.build();
  }

}
