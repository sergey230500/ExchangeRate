package com.example.demo.model.local;

import java.util.Map;

import com.example.demo.model.ExchangeRate;
import com.fasterxml.jackson.annotation.JsonInclude;

public class RateDetails {
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long id;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public Map<String, ExchangeRate> rates;
}
