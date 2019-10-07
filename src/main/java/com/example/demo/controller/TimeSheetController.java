package com.example.demo.controller;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ExchangeRate;
import com.example.demo.model.local.FilialInfo;
import com.example.demo.model.remote.FilialRateDTO;
import com.example.demo.service.DataLoaderService;
import com.example.demo.service.QueryBelarusBankService;

@RestController
@RequestMapping(path = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeSheetController {

  @Autowired
  private DataLoaderService dataService;
  @Autowired
  private QueryBelarusBankService queryService;

  @RequestMapping("/cities")
  public String[] getCities() throws IOException {
    return dataService.getCities();
  }

  @RequestMapping("/filials")
  public FilialInfo[] getFilials(@RequestParam("city") String cityName) throws IOException {
    return dataService.getFilials(cityName);
  }

  @RequestMapping("/rates")
  public Map<Long, Map<String, ExchangeRate>> getExchangeRates(
      @RequestParam("fil") List<Long> filials,
      @RequestParam(name = "cur", required = false) List<String> currencies) throws IOException {
    Set<Long> filialSet = new LinkedHashSet<>(filials);
    Set<String> currencySet = currencies == null ? null : new LinkedHashSet<>(currencies);
    return dataService.selectRates(filialSet, currencySet);
  }

  @RequestMapping("/data")
  public FilialRateDTO[] getAllData() throws IOException {
    return queryService.getRates();
  }
}
