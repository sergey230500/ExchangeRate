package com.example.demo.model.local;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Filial {
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long id;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String name;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public GPSCoordinates gps;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String address;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String phone;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String[] currencies;
}
