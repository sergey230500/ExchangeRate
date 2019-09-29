package com.example.demo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.local.CityInfo;
import com.example.demo.model.local.ExchangeRates;
import com.example.demo.model.local.FilialExchangeRates;
import com.example.demo.model.local.FilialInfo;
import com.example.demo.service.QueryBelarusBankService;

@RestController
@RequestMapping(path = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeSheetController {

  @Autowired
  private QueryBelarusBankService dataService;

  @RequestMapping("/cities")
  public List<CityInfo> getCities() {
    return Collections.singletonList(new CityInfo("г. Брест", 15));
  }

  @RequestMapping("/filials")
  public List<FilialInfo> getFilials(@RequestParam("city") String cityName) {
    return Collections.singletonList(new FilialInfo("Отделение 100\\/212", "пр. Партизанский 8-49"));
  }

  @RequestMapping("/rates")
  public Map<String, FilialExchangeRates> getExchangeRates(@RequestParam("fil") List<String> filials, @RequestParam("cur") List<String> currencies) {
    Map<String, FilialExchangeRates> result = new HashMap<>();
    Map<String, ExchangeRates> rates = new HashMap<>();
    rates.put("USD", new ExchangeRates(2.0460, 2.0700));
    rates.put("EUR", new ExchangeRates(2.2200, 2.2620));
    result.put("16", new FilialExchangeRates("16", rates));
    return result;
  }

  @RequestMapping("/data")
  public Object getAllData() {
    return dataService.getData();
  }
}
