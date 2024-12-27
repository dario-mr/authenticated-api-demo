package com.authenticated.api.demo.v1.controller;

import com.authenticated.api.demo.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class AuthenticatedController {

  private final JwtService jwtService;

  @GetMapping("hello-world")
  @Operation(security = @SecurityRequirement(name = "Authorization"))
  public String helloWorld() {
    return "Hello " + jwtService.getJwtSubject();
  }
}