package by.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  protected String cityType;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  protected String city;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  protected String streetType;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  protected String street;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  protected String house;

  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  @JsonIgnore
  public boolean isEmpty() {
    return Address.isEmpty(city) && Address.isEmpty(cityType) && Address.isEmpty(street) && Address.isEmpty(streetType) && Address.isEmpty(house);
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
}
