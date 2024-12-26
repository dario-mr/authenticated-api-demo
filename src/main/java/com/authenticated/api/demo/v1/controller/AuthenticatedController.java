package com.authenticated.api.demo.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class AuthenticatedController {

  @GetMapping("public/hello-world")
  public String publicHelloWorld() {
    return "Public Hello World";
  }

  @GetMapping("hello-world")
  @Operation(security = @SecurityRequirement(name = "Authorization"))
  public String helloWorld() {
    return "Hello World";
  }
}