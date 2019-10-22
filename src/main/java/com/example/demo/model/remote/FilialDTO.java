package com.example.demo.model.remote;

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
  public ScheduleDTO scheduleDTO;
  @JsonUnwrapped
  public AddressDTO addressDTO;
}
