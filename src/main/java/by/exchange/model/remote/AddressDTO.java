package by.exchange.model.remote;

import by.exchange.model.Address;
import com.fasterxml.jackson.annotation.JsonSetter;

public class AddressDTO extends Address {
  @JsonSetter("name_type")
  public void setCityType(String cityType) {
    super.setCityType(cityType);
  }

  @JsonSetter("name")
  public void setCity(String city) {
    super.setCity(city);
  }

  @JsonSetter("street_type")
  public void setStreetType(String streetType) {
    super.setStreetType(streetType);
  }

  @JsonSetter("street")
  public void setStreet(String street) {
    super.setStreet(street);
  }

  @JsonSetter("home_number")
  public void setHouse(String house) {
    super.setHouse(house);
  }
}
