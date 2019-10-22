package com.example.demo.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GPSCoordinates {
  @JsonProperty("long")
  public double longitude;
  @JsonProperty("lat")
  public double latitude;
}
