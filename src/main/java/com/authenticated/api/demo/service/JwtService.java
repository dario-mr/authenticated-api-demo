package com.authenticated.api.demo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  public String getJwtSubject() {
    // read JWT from the spring security context
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
      throw new IllegalStateException("Authentication required");
    }

    return jwt.getSubject();
  }
}
