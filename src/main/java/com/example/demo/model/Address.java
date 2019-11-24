package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(value = { "name_type", "name", "street_type", "home_number" }, ignoreUnknown = true, allowSetters = true)
public class Address {
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonSetter("name_type")
  public String cityType;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonSetter("name")
  public String city;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonSetter("street_type")
  public String streetType;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonSetter("street")
  public String street;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonSetter("home_number")
  public String house;

  @JsonIgnore
  public boolean isEmpty() {
    return isEmpty(city) && isEmpty(cityType) && isEmpty(street) && isEmpty(streetType) && isEmpty(house);
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String getValue() {
    StringBuilder result = new StringBuilder();
    if (cityType != null && city != null) result.append(cityType).append(' ').append(city);
    if (streetType != null && !streetType.isEmpty() && street != null && !street.isEmpty())
      result.append(", ").append(streetType).append(' ').append(street);
    if (house != null && !house.isEmpty()) result.append(", ").append(house);
    return result.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Address other = (Address) obj;
    if (isEmpty(city)) {
      if (!isEmpty(other.city)) return false;
    } else if (!city.equals(other.city)) return false;
    if (isEmpty(cityType)) {
      if (!isEmpty(other.cityType)) return false;
    } else if (!cityType.equals(other.cityType)) return false;
    if (isEmpty(house)) {
      if (!isEmpty(other.house)) return false;
    } else if (!house.equals(other.house)) return false;
    if (isEmpty(street)) {
      if (!isEmpty(other.street)) return false;
    } else if (!street.equals(other.street)) return false;
    if (isEmpty(streetType)) {
      if (!isEmpty(other.streetType)) return false;
    } else if (!streetType.equals(other.streetType)) return false;
    return true;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 0;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((cityType == null) ? 0 : cityType.hashCode());
    result = prime * result + ((house == null) ? 0 : house.hashCode());
    result = prime * result + ((street == null) ? 0 : street.hashCode());
    result = prime * result + ((streetType == null) ? 0 : streetType.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return getValue();
  }

  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
