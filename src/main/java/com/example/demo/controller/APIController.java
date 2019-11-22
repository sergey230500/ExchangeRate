package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Address;
import com.example.demo.model.FilialService;
import com.example.demo.service.DataLoaderService;

@RestController
@RequestMapping(path = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

  @Autowired
  private DataLoaderService dataService;

  @RequestMapping(path = "/search")
  public Object search(@RequestParam(name = "q", required = true) String query) {
    return null;
  }

  @RequestMapping(path = "/filials")
  public List<?> findFilials(
      @RequestParam(name = "id", required = false) Set<Integer> ids,
      @RequestParam(name = "cur", required = false) Set<String> currencies,
      Address address
  // TODO фильтр по расписанию
  ) {
    return null;
  }

  @RequestMapping(path = "/rates")
  public List<?> getRates(
      @RequestParam(name = "id", required = true) Set<Integer> ids,
      @RequestParam(name = "cur", required = false) Set<String> currencies) {
    return null;
  }

  @RequestMapping(path = "/place")
  public Address findPlace(
      @RequestParam(name = "lon", required = true) double lon,
      @RequestParam(name = "lat", required = true) double lat) {
    return null;
  }

  @RequestMapping("/services")
  public FilialService[] getServiceList() throws IOException {
    return FilialService.values();
  }
}
