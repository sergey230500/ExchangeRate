package com.example.demo.model.local;

import com.example.demo.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Filial {
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long id;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String name;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public GPSCoordinates gps;
  @JsonIgnore
  public Address address;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String phone;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String[] currencies;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  @JsonProperty("address")
  public String getAddressValue() {
    return address == null ? null : address.getValue();
  }
}
