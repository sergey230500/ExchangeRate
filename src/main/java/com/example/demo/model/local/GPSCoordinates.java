package com.example.demo.model.local;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GPSCoordinates {
  @JsonProperty("lon")
  public double longitude;
  @JsonProperty("lat")
  public double latitude;

  public GPSCoordinates() {
  }

  public GPSCoordinates(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public GPSCoordinates(GPSCoordinates other) {
    this.longitude = other.longitude;
    this.latitude = other.latitude;
  }

  @JsonIgnore
  public boolean isZero() {
    return longitude == 0.0 && latitude == 0.0;
  }
}
