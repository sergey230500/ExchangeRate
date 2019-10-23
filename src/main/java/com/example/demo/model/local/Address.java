package com.example.demo.model.local;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Address {
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String cityType;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String city;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String streetType;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String street;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String house;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String value;

  @Override
  public String toString() {
    return value;
  }
}
