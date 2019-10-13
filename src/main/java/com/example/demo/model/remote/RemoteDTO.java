package com.example.demo.model.remote;

import com.fasterxml.jackson.annotation.JsonSetter;

public class RemoteDTO {
  @JsonSetter(value = "filial_id")
  public long id;
}
