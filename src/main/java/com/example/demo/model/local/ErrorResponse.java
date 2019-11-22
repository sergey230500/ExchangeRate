package com.example.demo.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
  @JsonProperty
  public String message;

  public ErrorResponse() {
  }

  public ErrorResponse(String message) {
    this.message = message;
  }
}
