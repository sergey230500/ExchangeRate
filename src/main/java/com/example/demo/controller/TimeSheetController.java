package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.local.CityInfo;
import com.example.demo.model.local.FilialExchangeRates;
import com.example.demo.model.local.FilialInfo;
import com.example.demo.model.remote.Filial;
import com.example.demo.service.QueryBelarusBankService;

@RestController
@RequestMapping(path = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeSheetController {

  @Autowired
  private QueryBelarusBankService dataService;

  @RequestMapping("/cities")
  public List<CityInfo> getCities() throws IOException {
    return dataService.getCities();
  }

  @RequestMapping("/filials")
  public List<FilialInfo> getFilials(@RequestParam("city") String cityName)  throws IOException{
    return dataService.getFilials(cityName);
  }

  @RequestMapping("/rates")
  public Map<String, FilialExchangeRates> getExchangeRates(
      @RequestParam("fil") Set<String> filials,
      @RequestParam("cur") Set<String> currencies) throws IOException {
    return null; // TODO
  }

  @RequestMapping("/data")
  public List<Filial> getAllData() throws IOException {
    return dataService.getData();
  }

  @RequestMapping("/search")
  public Filial findFilial(@RequestParam("id") String id) throws IOException {
    return dataService.findFilial(id);
  }
}
