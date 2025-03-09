package com.om.flagexplorer.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiSpecController {

  @GetMapping(value = "/api-spec.yaml", produces = "application/yaml")
  public ResponseEntity<Resource> getApiSpec() {
    return ResponseEntity.ok(new ClassPathResource("api-spec.yaml"));
  }
}
