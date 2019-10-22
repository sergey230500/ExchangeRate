package com.example.demo.model.remote;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class FilialsInfoDTO extends FilialDTO {
  @JsonSetter("GPS_X")
  public double longitude;

  @JsonSetter("GPS_Y")
  public double latitude;

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

  @JsonUnwrapped(suffix = "_prev")
  public AddressDTO previousAddress;

  public Map<String, Boolean> services;

  @JsonAnySetter
  public void setService(String service, String value) {
    if (service.startsWith("usl_")) {
      String key = service.substring(4);
      if (services == null) services = new LinkedHashMap<>(97);
      services.put(key, Integer.parseInt(value) > 0);
    }
  }
}
