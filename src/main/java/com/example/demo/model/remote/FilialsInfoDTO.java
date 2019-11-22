package com.example.demo.model.remote;

import java.util.EnumSet;
import java.util.Set;

import com.example.demo.model.Address;
import com.example.demo.model.FilialService;
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
  public Address previousAddress;

  public Set<FilialService> services;

  @JsonAnySetter
  public void setService(String service, String value) {
    if (service.startsWith("usl_")) {
      String key = service.substring(4);
      if (services == null) services = EnumSet.noneOf(FilialService.class);
      if (Integer.parseInt(value) > 0) services.add(FilialService.valueOf(key));
    }
  }
}
