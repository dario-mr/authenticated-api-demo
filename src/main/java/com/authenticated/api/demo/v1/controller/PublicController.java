package com.authenticated.api.demo.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/public")
public class PublicController {

  @GetMapping("hello-world")
  public String publicHelloWorld() {
    return "Public Hello World";
  }
}