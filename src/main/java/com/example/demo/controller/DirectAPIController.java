package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.local.Filial;
import com.example.demo.service.DataLoaderService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/api/direct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectAPIController {
  @Autowired
  private DataLoaderService dataService;

  @JsonView(Filial.Basic.class)
  @GetMapping("/short")
  public Object getShortFormat() throws IOException {
    return dataService.getAllFilials();
  }

  @JsonView(Filial.Detailed.class)
  @GetMapping("/long")
  public Object getLongFormat() throws IOException {
    return dataService.getAllFilials();
  }
}
