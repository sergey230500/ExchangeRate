package com.example.demo.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.example.demo.model.Address;
import com.example.demo.model.local.Filial;
import com.example.demo.model.local.GPSCoordinates;
import com.example.demo.model.local.RateDetails;
import com.example.demo.model.remote.FilialsInfoDTO;
import com.example.demo.model.remote.KursExchangeDTO;
import com.example.demo.model.remote.RemoteDTO;
import com.example.demo.service.CityLocator.CityAddress;

@Service
public class DataLoaderService {
  @Autowired
  private Converter<FilialsInfoDTO, Filial> shortConverter;
  @Autowired
  private Converter<KursExchangeDTO, RateDetails> ratesConverter;

  @Autowired
  private QueryBelarusBankService queryService;

  private Map<Long, Filial> filials;
  private Map<Long, RateDetails> rates;

  private CityLocator locator;

  public Map<Long, Filial> getAllFilials() throws IOException {
    if (filials == null) {
      filials = convertToMap(queryService.getFilials(), shortConverter);
      final String[] emptyList = new String[0];
      final Map<Long, RateDetails> allRates = getAllRates();
      filials.forEach((id, filial) -> {
        RateDetails details = allRates.get(id);
        if (details == null) filial.currencies = emptyList;
        else filial.currencies = details.rates.keySet().toArray(new String[details.rates.size()]);
      });
    }
    return filials;
  }

  public Map<Long, RateDetails> getAllRates() throws IOException {
    if (rates == null) rates = convertToMap(queryService.getRates(), ratesConverter);
    return rates;
  }

  public Address findClosest(GPSCoordinates location) throws IOException {
    if (locator == null) locator = new CityLocator(getAllFilials().values());
    CityAddress closest = locator.findClosest(location);
    if (closest == null) return null;
    Address result = new Address();
    result.cityType = closest.cityType;
    result.city = closest.city;
    return result;
  }

  private static <S extends RemoteDTO, T> Map<Long, T> convertToMap(S[] rawFilials, Converter<S, T> converter) {
    final int count = rawFilials.length;
    Map<Long, T> result = new LinkedHashMap<>(count);
    for (int i = 0; i < count; ++i)
      result.put(rawFilials[i].id, converter.convert(rawFilials[i]));
    return result;
  }
}
