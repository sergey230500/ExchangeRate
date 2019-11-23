package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DataLoaderService;

@RestController
@RequestMapping(path = "/api/direct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectAPIController {
  @Autowired
  private DataLoaderService dataService;

  @GetMapping("/short")
  public Object getShortFormat() throws IOException {
    return dataService.getAllFilials();
  }

  @GetMapping("/long")
  public Object getLongFormat() throws IOException {
    return dataService.getAllFilialDetails();
  }
}
