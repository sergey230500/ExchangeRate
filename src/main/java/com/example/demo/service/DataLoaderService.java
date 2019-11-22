package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.example.demo.model.local.Filial;
import com.example.demo.model.local.FilialDetails;
import com.example.demo.model.remote.FilialsInfoDTO;
import com.example.demo.model.remote.KursExchangeDTO;

@Service
public class DataLoaderService {
  @Autowired
  private Converter<FilialsInfoDTO, Filial> shortConverter;
  @Autowired
  private Converter<FilialsInfoDTO, FilialDetails> longConverter;
  @Autowired
  private QueryBelarusBankService queryService;

  private String[] cities;

  public void reset() {
    cities = null;
  }

  public String[] getCities() throws IOException {
    if (this.cities == null) {
      KursExchangeDTO[] rates = queryService.getRates();
      final int count = rates.length;
      List<String> cities = new ArrayList<>();
      for (int i = 0; i < count; ++i)
        cities.add(rates[i].address.city);
      Set<String> citySet = new TreeSet<>(cities);
      this.cities = citySet.toArray(new String[citySet.size()]);
    }

    return cities;
  }

  public List<Filial> getFilials() throws IOException {
    return convertFilials(queryService.getFilials(), shortConverter);
  }

  public List<FilialDetails> getFilialDetails() throws IOException {
    return convertFilials(queryService.getFilials(), longConverter);
  }

  private static <T> List<T> convertFilials(FilialsInfoDTO[] rawFilials, Converter<FilialsInfoDTO, T> converter) {
    final int count = rawFilials.length;
    List<T> result = new ArrayList<>(count);
    for (int i = 0; i < count; ++i)
      result.add(converter.convert(rawFilials[i]));
    return result;
  }
}
