package com.example.demo.model.local;

public class Address {
  public String cityType;
  public String city;
  public String streetType;
  public String street;
  public String house;
  public String value;

  @Override
  public String toString() {
    return value;
  }
}
