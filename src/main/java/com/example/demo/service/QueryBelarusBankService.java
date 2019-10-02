package com.example.demo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.remote.Filial;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QueryBelarusBankService {

  private static final TypeReference<List<Filial>> RESULT_TYPE = new TypeReference<List<Filial>>() {
  };

  @Autowired
  private ObjectMapper mapper;
  @Autowired
  private RestTemplate dataTemplate;

  public List<Filial> data;
  // TODO отсортировать список по id ИЛИ
  // использовать Map для хранения
  public List<Filial> getData() throws IOException {
    if (data == null) {
      String rawResponse = dataTemplate.getForObject("https://belarusbank.by/api/kursExchange", String.class);
      data = mapper.readValue(rawResponse, RESULT_TYPE);
    }
    return data;
  }

  public List<Filial> getDataById(Set<String> filials) {
    // TODO
    return null;
  }

  public Map<String, Integer> countCities() {
    Map<String, Integer> cities = new HashMap<>();
    for (Filial item: data) {
      String cityName = item.getCity();
      if (!cities.containsKey(cityName)) {
        cities.put(cityName, 0);
      } else
        cities.put(cityName, cities.get(cityName) + 1);
    }
    return cities;
  }
}
