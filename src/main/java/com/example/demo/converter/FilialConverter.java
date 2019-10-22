package com.example.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.local.Address;
import com.example.demo.model.local.Filial;
import com.example.demo.model.local.GPSCoordinates;
import com.example.demo.model.remote.AddressDTO;
import com.example.demo.model.remote.FilialsInfoDTO;

@Component
public class FilialConverter implements Converter<FilialsInfoDTO, Filial> {
  @Autowired
  private Converter<AddressDTO, Address> addressConverter;

  @Override
  public Filial convert(FilialsInfoDTO source) {
    Filial result = new Filial();
    result.id = source.id;

    result.internalId = source.internalId;
    result.name = source.name;
    result.coordinates = new GPSCoordinates();
    result.coordinates.longitude = source.longitude;
    result.coordinates.latitude = source.latitude;
    result.address = addressConverter.convert(source.address);
    result.schedule = source.schedule;
    result.phoneNumber = source.phoneNumber;

    return result;
  }
}
