package com.example.demo.model.remote;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FilialsInfoDTO extends RemoteDTO {
  private static final Logger LOG = LoggerFactory.getLogger(FilialsInfoDTO.class);

  @JsonProperty("GPS_X")
  //@JsonFormat(shape=Shape.STRING)
  public Double longitude;

  @JsonProperty("GPS_Y")
  public Double latitude;

  @JsonProperty("filial_num")
  public int filialNum;

  @JsonProperty("otd_num")
  public String department;

  @JsonProperty("cbu_num")
  public int cbuNum;

  @JsonProperty("name_type_prev")
  public String previousNameType;

  @JsonProperty("name_prev")
  public String previousName;

  @JsonProperty("street_type_prev")
  public String previousStreetType;

  @JsonProperty("street_prev")
  public String previousStreet;

  @JsonProperty("home_number_prev")
  public String previousHouse;

  @JsonProperty("info_text")
  public String info;

  @JsonProperty("info_bank_bik")
  public String bik;

  @JsonProperty("info_bank_unp")
  public String unp;

  @JsonProperty("bel_number_schet")
  public String belAccountNumber;

  @JsonProperty("foreign_number_schet")
  public String foreignAccountNumber;

  @JsonProperty("phone_info")
  public String phoneNumber;

  @JsonIgnore
  public Map<String, Boolean> services;

  @JsonAnySetter
  public void setService(String service, String value) {
    if (service.startsWith("usl_")) {
      String key = service.substring(4);
      if (services == null) services = new LinkedHashMap<>(40);
      services.put(key, Integer.parseInt(value) > 0);
    } else {
      // LOG.warn("Unknown property {}", service);
    }
  }
}
