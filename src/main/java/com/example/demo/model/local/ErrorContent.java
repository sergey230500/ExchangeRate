package com.example.demo.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
public class ErrorContent {
  @JsonProperty
  public String message;

  public ErrorContent() {
  }

  public ErrorContent(String message) {
    this.message = message;
  }
}
