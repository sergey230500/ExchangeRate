package by.exchange.model.remote;

import by.exchange.model.Schedule;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class FilialDTO extends RemoteDTO {
  @JsonSetter(value = "sap_id")
  public long internalId;
  @JsonSetter(value = "filials_text")
  @JsonAlias(value = "filial_name")
  public String name;
  @JsonSetter(value = "info_worktime")
  public Schedule schedule;
  @JsonUnwrapped
  public AddressDTO address;
}
