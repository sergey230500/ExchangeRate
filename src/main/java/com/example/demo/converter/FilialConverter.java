package com.example.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.local.Filial;
import com.example.demo.model.local.GPSCoordinates;
import com.example.demo.model.remote.FilialsInfoDTO;

@Component
public class FilialConverter implements Converter<FilialsInfoDTO, Filial> {
  @Override
  public Filial convert(FilialsInfoDTO source) {
    Filial result = new Filial();
    result.id = source.id;

    result.name = source.name;
    result.gps = new GPSCoordinates();
    result.gps.longitude = source.longitude;
    result.gps.latitude = source.latitude;
    result.address = source.address.getValue();
    result.phone = source.phoneNumber;

    return result;
  }
}
