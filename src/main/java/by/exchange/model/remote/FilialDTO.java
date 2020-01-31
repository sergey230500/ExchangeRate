package by.exchange.model.remote;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import by.exchange.model.Address;
import by.exchange.model.Schedule;

public class FilialDTO extends RemoteDTO {
  @JsonSetter(value = "sap_id")
  public long internalId;
  @JsonSetter(value = "filials_text")
  @JsonAlias(value = "filial_name")
  public String name;
  @JsonSetter(value = "info_worktime")
  public Schedule schedule;
  @JsonUnwrapped
  public Address address;
}
