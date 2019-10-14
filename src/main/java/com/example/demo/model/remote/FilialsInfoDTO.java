package com.example.demo.model.remote;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class FilialsInfoDTO extends FilialDTO {
  private static final Logger LOG = LoggerFactory.getLogger(FilialsInfoDTO.class);

  @JsonSetter("GPS_X")
  public Double longitude;

  @JsonSetter("GPS_Y")
  public Double latitude;

  @JsonSetter("filial_num")
  public int filialNum;

  @JsonSetter("otd_num")
  public String department;

  @JsonSetter("cbu_num")
  public int cbuNum;

  @JsonSetter("dop_num")
  public String extraNum;

  @JsonSetter("info_text")
  public String info;

  @JsonSetter("info_bank_bik")
  public String bik;

  @JsonSetter("info_bank_unp")
  public String unp;

  @JsonSetter("bel_number_schet")
  public String belAccountNumber;

  @JsonSetter("foreign_number_schet")
  public String foreignAccountNumber;

  @JsonSetter("phone_info")
  public String phoneNumber;

  @JsonUnwrapped(suffix="_prev")
  public Address previousAddress;

  public Map<String, Boolean> services;

  @JsonAnySetter
  public void setService(String service, String value) {
    if (service.startsWith("usl_")) {
      String key = service.substring(4);
      if (services == null) services = new LinkedHashMap<>(40);
      services.put(key, Integer.parseInt(value) > 0);
    } else {
      LOG.debug("Unknown property {}", service);
    }
  }
}
