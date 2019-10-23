package com.example.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.local.Address;
import com.example.demo.model.local.FilialDetails;
import com.example.demo.model.local.FilialDetails.Services;
import com.example.demo.model.remote.AddressDTO;
import com.example.demo.model.remote.FilialsInfoDTO;

@Component
public class FilialDetailsConverter implements Converter<FilialsInfoDTO, FilialDetails> {
  @Autowired
  private Converter<AddressDTO, Address> addressConverter;

  @Override
  public FilialDetails convert(FilialsInfoDTO source) {
    FilialDetails result = new FilialDetails();
    result.id = source.id;
    result.internalId = source.internalId;

    result.filialNum = source.filialNum;
    result.departmentNum = source.department;
    result.bankingCenterNum = source.cbuNum;
    result.extraNum = source.extraNum;

    result.address = addressConverter.convert(source.address);
    result.schedule = source.schedule;

    result.info = source.info;
    result.bankIdCode = source.bik;
    result.payerAccountNumber = source.unp;
    result.belAccountNumber = source.belAccountNumber;
    result.foreignAccountNumber = source.foreignAccountNumber;
    result.previousAddress = addressConverter.convert(source.previousAddress);
    result.services = new Services(source.services);

    return result;
  }
}
