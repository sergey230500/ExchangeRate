package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QueryBelarusBankService {
  @Autowired
  private RestTemplate dataTemplate;

  public QueryBelarusBankService() {
  }

  public List<?> getData() {
    List<?> data = dataTemplate.getForObject("https://belarusbank.by/api/kursExchange", List.class);

    return data;
  }
}
