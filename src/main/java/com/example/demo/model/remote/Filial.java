package com.example.demo.model.remote;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.model.local.ExchangeRates;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;

@JsonDeserialize(converter = Filial.FilialDeserializePostProcessor.class)
public class Filial {

  // "info_worktime": "Пн 9 00 19 00    |Вт 9 00 19 00    |Ср 9 00 19 00    |Чт 9 00 19 00    |Пт 9 00 19 00    |Сб 9 00 15 00    |Вс        |",
  @JsonIgnore
  //@JsonProperty(value = "info_worktime")
  public Object schedule;

  @JsonSetter(value = "filial_id")
  public String id;
  @JsonSetter(value = "sap_id")
  public String internalId;
  @JsonSetter(value = "filials_text")
  public String name;

  private String cityType;
  private String city;
  private String streetType;
  private String street;
  private String house;

  private Map<String, BigDecimal> inRates = new HashMap<>();
  private Map<String, BigDecimal> outRates = new HashMap<>();
  private Map<String, ExchangeRates> rates;

  @JsonAnySetter
  public void setRate(String key, String value) {
    if (key.endsWith("_in")) {
      key = key.substring(0, key.length() - 3);
      inRates.put(key, new BigDecimal(value));
    } else if (key.endsWith("_out")) {
      key = key.substring(0, key.length() - 4);
      outRates.put(key, new BigDecimal(value));
    } else {
      // TODO add logging
    }
  }

  @JsonGetter
  public String getAddress() {
    return String.format("%s %s, %s %s, %s", cityType, city, streetType, street, house);
  }

  @JsonGetter
  public Map<String, ExchangeRates> getRates() {
    return rates;
  }

  @JsonGetter("id")
  public String getId() {
    return id;
  }

  @JsonGetter("internalId")
  public String getInternalId() {
    return internalId;
  }

  @JsonGetter("name")
  public String getName() {
    return name;
  }

  @JsonIgnore
  public String getCityType() {
    return cityType;
  }

  @JsonIgnore
  public String getCity() {
    return city;
  }

  @JsonIgnore
  public String getStreetType() {
    return streetType;
  }

  @JsonIgnore
  public String getStreet() {
    return street;
  }

  @JsonIgnore
  public String getHouse() {
    return house;
  }

  @JsonIgnore
  public void setId(String id) {
    this.id = id;
  }

  @JsonIgnore
  public void setInternalId(String internalId) {
    this.internalId = internalId;
  }

  @JsonIgnore
  public void setName(String name) {
    this.name = name;
  }

  @JsonSetter(value = "name_type")
  public void setCityType(String cityType) {
    this.cityType = cityType;
  }

  @JsonSetter(value = "name")
  public void setCity(String city) {
    this.city = city;
  }

  @JsonSetter(value = "street_type")
  public void setStreetType(String streetType) {
    this.streetType = streetType;
  }

  @JsonSetter
  public void setStreet(String street) {
    this.street = street;
  }

  @JsonSetter(value = "home_number")
  public void setHouse(String house) {
    this.house = house;
  }

  public static class FilialDeserializePostProcessor extends StdConverter<Filial, Filial> {
    @Override
    public Filial convert(Filial host) {
      host.rates = new HashMap<>(host.inRates.size());

      for (Map.Entry<String, BigDecimal> entry: host.inRates.entrySet()) {
        String currency = entry.getKey();
        BigDecimal inRate = entry.getValue();
        BigDecimal outRate = host.outRates.remove(currency);

        if (inRate != null && BigDecimal.ZERO.compareTo(inRate) == 0)
          inRate = null;
        if (outRate != null && BigDecimal.ZERO.compareTo(outRate) == 0)
          outRate = null;

        if (inRate != null || outRate != null)
          host.rates.put(currency, new ExchangeRates(inRate, outRate));
      }

      for (Map.Entry<String, BigDecimal> entry: host.outRates.entrySet()) {
        String currency = entry.getKey();
        BigDecimal outRate = entry.getValue();

        if (outRate != null && BigDecimal.ZERO.compareTo(outRate) != 0)
          host.rates.put(currency, new ExchangeRates(null, outRate));
      }

      host.inRates = null;
      host.outRates = null;

      return host;
    }
  }
}
