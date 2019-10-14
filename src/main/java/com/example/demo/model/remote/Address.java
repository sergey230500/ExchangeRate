package com.example.demo.model.remote;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Address {
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

  public String getValue() {
    StringBuilder result = new StringBuilder();
    if (cityType != null && city != null) result.append(cityType).append(' ').append(city);
    if (streetType != null && !streetType.isEmpty() && street != null && !street.isEmpty()) result.append(", ").append(streetType).append(' ').append(street);
    if (house != null && !house.isEmpty()) result.append(", ").append(house);
    return result.toString();
  }
}
