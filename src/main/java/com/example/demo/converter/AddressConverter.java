package com.example.demo.converter;

import org.springframework.core.convert.converter.Converter;

import com.example.demo.model.local.Address;
import com.example.demo.model.remote.AddressDTO;

public class AddressConverter implements Converter<AddressDTO, Address> {
  @Override
  public Address convert(AddressDTO source) {
    Address result = new Address();
    result.cityType = source.cityType;
    result.city = source.city;
    result.streetType = source.streetType;
    result.street = source.street;
    result.house = source.house;
    result.value = source.getValue();
    return result;
  }
}
