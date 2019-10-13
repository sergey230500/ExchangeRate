package com.example.demo.model.remote;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.ExchangeRate;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class KursExchangeDTO extends RemoteDTO {
  private static final Logger LOG = LoggerFactory.getLogger(KursExchangeDTO.class);

  public Map<String, ExchangeRate> rates;
  @JsonSetter(value = "sap_id")
  public long internalId;
  @JsonSetter(value = "filials_text")
  public String name;
  @JsonSetter(value = "name_type")
  public String cityType;
  @JsonSetter(value = "name")
  public String city;
  @JsonSetter(value = "street_type")
  public String streetType;
  @JsonSetter(value = "street")
  public String street;
  @JsonSetter(value = "home_number")
  public String house;
  @JsonSetter(value = "info_worktime")
  public Schedule schedule;

  @JsonAnySetter
  public void setRate(String key, String value) {
    boolean in = key.endsWith("_in");
    boolean out = key.endsWith("_out");
    if (in) {
      key = key.substring(0, key.length() - 3);
    } else if (out) {
      key = key.substring(0, key.length() - 4);
    } else {
      LOG.warn("Unknown property {}", key);
      return;
    }
    BigDecimal rate = new BigDecimal(value);
    if (BigDecimal.ZERO.compareTo(rate) < 0) {
      if (rates == null) rates = new LinkedHashMap<>();

      ExchangeRate pair = rates.get(key);
      if (pair == null) rates.put(key, pair = new ExchangeRate());

      if (in) pair.in = rate;
      else pair.out = rate;
    }
  }

  public String getAddress() {
    StringBuilder result = new StringBuilder();
    result.append(cityType).append(' ').append(city);
    if (streetType != null && !streetType.isEmpty() && street != null && !street.isEmpty()) result.append(", ").append(streetType).append(' ').append(street);
    if (house != null && !house.isEmpty()) result.append(", ").append(house);
    return result.toString();
  }
}
