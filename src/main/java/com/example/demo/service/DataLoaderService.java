package com.example.demo.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.example.demo.model.ExchangeRate;
import com.example.demo.model.local.Filial;
import com.example.demo.model.local.FilialDetails;
import com.example.demo.model.local.FilialInfo;
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

  @Cacheable("filialsByCity")
  public FilialInfo[] getFilials(String city) throws IOException {
    // TODO introduce index
    KursExchangeDTO[] rates = queryService.getRates();
    final int count = rates.length;
    List<FilialInfo> result = new ArrayList<>();
    for (int i = 0; i < count; ++i)
      if (city.equalsIgnoreCase(rates[i].address.city)) result.add(convert(rates[i]));
    return result.toArray(new FilialInfo[result.size()]);
  }

  private FilialInfo convert(KursExchangeDTO rawData) {
    FilialInfo result = new FilialInfo();
    result.id = rawData.id;
    result.name = rawData.name;
    result.address = rawData.address.getValue();
    result.currencies = rawData.rates == null ? new String[0] : rawData.rates.keySet().toArray(new String[rawData.rates.size()]);
    return result;
  }

  public Map<Long, Map<String, ExchangeRate>> selectRates(Set<Long> filialSet, Set<String> currencySet) throws IOException {
    KursExchangeDTO[] rates = queryService.getRates();
    Map<Long, Map<String, ExchangeRate>> result = new LinkedHashMap<>(filialSet.size());
    KursExchangeDTO dummy = new KursExchangeDTO();
    for (Long filialId: filialSet) {
      dummy.id = filialId;
      int index = Arrays.binarySearch(rates, dummy, QueryBelarusBankService.BY_ID);
      if (index >= 0) {
        KursExchangeDTO found = rates[index];
        if (currencySet == null || currencySet.isEmpty()) result.put(filialId, found.rates);
        else result.put(filialId, found.rates.entrySet().stream().filter(e -> currencySet.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
      }
    }
    return result;
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
