package com.example.demo.controller;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FilialService;
import com.example.demo.model.local.Address;
import com.example.demo.service.DataLoaderService;

@RestController
@RequestMapping(path = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

  @Autowired
  private DataLoaderService dataService;

  @RequestMapping(path = "/filials")
  public Object findFilials(
      @RequestParam(name = "id", required = false) Set<Integer> ids,
      @RequestParam(name = "cur", required = false) Set<String> currencies,
      Address address) {
    return null;
  }

  @RequestMapping("/services")
  public FilialService[] getServiceList() throws IOException {
    return FilialService.values();
  }

}
