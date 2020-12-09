package by.exchange.model.local;

import by.exchange.model.Address;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = Id.NAME, property = "type", visible = false)
public abstract class SearchResult {

  @JsonTypeName("city")
  public static class City extends SearchResult {
    public String cityType;
    public String city;

    public City() {
    }

    public City(Address address) {
      this.cityType = address.getCityType();
      this.city = address.getCity();
    }

    public City(String cityType, String city) {
      this.cityType = cityType;
      this.city = city;
    }
  }

  @JsonTypeName("street")
  public static class Street extends City {
    public String streetType;
    public String street;

    public Street() {
    }

    public Street(Address address) {
      super(address);
      this.streetType = address.getStreetType();
      this.street = address.getStreet();
    }

    public Street(String cityType, String city, String streetType, String street) {
      super(cityType, city);
      this.streetType = streetType;
      this.street = street;
    }
  }

  protected static abstract class FilialBase extends SearchResult {
    public long id;
    public String name;

    protected FilialBase() {
    }

    protected FilialBase(Filial filial) {
      this.id = filial.id;
      this.name = filial.name;
    }

    protected FilialBase(long id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  @JsonTypeName("id")
  public static class FilialId extends FilialBase {
    public FilialId() {
      super();
    }

    public FilialId(Filial filial) {
      super(filial);
    }

    public FilialId(long id, String name) {
      super(id, name);
    }
  }

  @JsonTypeName("name")
  public static class FilialName extends FilialBase {
    public FilialName() {
      super();
    }

    public FilialName(Filial filial) {
      super(filial);
    }

    public FilialName(long id, String name) {
      super(id, name);
    }
  }
}
