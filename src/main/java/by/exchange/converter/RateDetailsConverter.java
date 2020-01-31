package by.exchange.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import by.exchange.model.local.RateDetails;
import by.exchange.model.remote.KursExchangeDTO;

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
