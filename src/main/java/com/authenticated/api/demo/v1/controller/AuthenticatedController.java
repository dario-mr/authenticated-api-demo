package com.authenticated.api.demo.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticatedController {

  @GetMapping("hello-world")
  public String helloWorld() {
    return "Hello World";
  }

  @GetMapping("public/hello-world")
  public String publicHelloWorld() {
    return "Public Hello World";
  }

}