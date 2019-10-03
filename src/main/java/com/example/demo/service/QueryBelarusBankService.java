package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.local.CityInfo;
import com.example.demo.model.local.FilialInfo;
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

  private List<Filial> data;
  private Map<String, List<Filial>> filialsByCity;
  private Map<String, Filial> filialsById;

  public List<Filial> getData() throws IOException {
    if (data == null) {
      String rawResponse = dataTemplate.getForObject("https://belarusbank.by/api/kursExchange", String.class);
      data = mapper.readValue(rawResponse, RESULT_TYPE);
    }
    return data;
  }

  private void initStructures() throws IOException {
    if (data == null)
      getData();

    filialsByCity = data.stream().sequential()
        .collect(Collectors.groupingBy(Filial::getCity, TreeMap::new, Collectors.toList()));

    filialsById = data.stream().sequential()
        .collect(Collectors.toMap(f -> f.id, Function.identity()));
  }

  public List<CityInfo> getCities() throws IOException {
    if (filialsByCity == null)
      initStructures();
    return filialsByCity.entrySet().stream()
        .map(entry -> new CityInfo(entry.getKey(), entry.getValue().size()))
        .collect(Collectors.toList());
  }
  
  public List<FilialInfo> getFilials(String cityName) throws IOException{
    if (filialsByCity == null)
      initStructures();
    return filialsByCity.getOrDefault(cityName, Collections.emptyList())
        .stream()
        .map(f -> {
          FilialInfo result = new FilialInfo();
          
          result.id = f.id;
          result.name = f.name;
          result.address = f.getAddress();
          result.currencies = new ArrayList<>(f.getRates().keySet());
          
          return result;
        }).collect(Collectors.toList());
  }

  public Filial findFilial(String id) throws IOException {
    if (filialsById == null)
      initStructures();
    return filialsById.get(id);
  }
}
