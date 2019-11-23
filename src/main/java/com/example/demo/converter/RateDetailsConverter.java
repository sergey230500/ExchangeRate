package com.example.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.local.RateDetails;
import com.example.demo.model.remote.KursExchangeDTO;

@Component
public class RateDetailsConverter implements Converter<KursExchangeDTO, RateDetails> {
  @Override
  public RateDetails convert(KursExchangeDTO source) {
    RateDetails result = new RateDetails();

    result.id = source.id;
    result.rates = source.rates;

    return result;
  }
}
