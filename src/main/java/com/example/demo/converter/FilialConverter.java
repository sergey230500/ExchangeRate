package com.example.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.local.Filial;
import com.example.demo.model.local.Filial.Services;
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
    result.address = source.address == null || source.address.isEmpty() ? null : source.address;
    result.phone = source.phoneNumber;

    result.internalId = source.internalId;
    result.filialNum = source.filialNum;
    result.departmentNum = source.department;
    result.bankingCenterNum = source.cbuNum;
    result.extraNum = source.extraNum;
    result.schedule = source.schedule;

    result.info = source.info;
    result.bankIdCode = source.bik;
    result.payerAccountNumber = source.unp;
    result.belAccountNumber = source.belAccountNumber;
    result.foreignAccountNumber = source.foreignAccountNumber;
    result.previousAddress = source.previousAddress == null || source.previousAddress.isEmpty() ? null : source.previousAddress;
    result.services = new Services(source.services);

    return result;
  }
}
