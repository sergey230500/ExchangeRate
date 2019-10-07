package com.example.demo.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.model.ExchangeRate;
import com.example.demo.model.local.FilialInfo;
import com.example.demo.model.remote.FilialRateDTO;

@Service
public class DataLoaderService {
  @Autowired
  private QueryBelarusBankService queryService;

  private String[] cities;

  public void reset() {
    cities = null;
  }

  public String[] getCities() throws IOException {
    if (this.cities == null) {
      FilialRateDTO[] rates = queryService.getRates();
      final int count = rates.length;
      List<String> cities = new ArrayList<>();
      for (int i = 0; i < count; ++i)
        cities.add(rates[i].city);
      Set<String> citySet = new TreeSet<>(cities);
      this.cities = citySet.toArray(new String[citySet.size()]);
    }

    return cities;
  }

  @Cacheable("filialsByCity")
  public FilialInfo[] getFilials(String city) throws IOException {
    // TODO introduce index
    FilialRateDTO[] rates = queryService.getRates();
    final int count = rates.length;
    List<FilialInfo> result = new ArrayList<>();
    for (int i = 0; i < count; ++i)
      if (city.equalsIgnoreCase(rates[i].city)) result.add(convert(rates[i]));
    return result.toArray(new FilialInfo[result.size()]);
  }

  private FilialInfo convert(FilialRateDTO rawData) {
    FilialInfo result = new FilialInfo();
    result.id = rawData.id;
    result.name = rawData.name;
    result.address = rawData.getAddress();
    result.currencies = rawData.rates == null ? new String[0] : rawData.rates.keySet().toArray(new String[rawData.rates.size()]);
    return result;
  }

  public Map<Long, Map<String, ExchangeRate>> selectRates(Set<Long> filialSet, Set<String> currencySet) throws IOException {
    FilialRateDTO[] rates = queryService.getRates();
    Map<Long, Map<String, ExchangeRate>> result = new LinkedHashMap<>(filialSet.size());
    FilialRateDTO dummy = new FilialRateDTO();
    for (Long filialId: filialSet) {
      dummy.id = filialId;
      int index = Arrays.binarySearch(rates, dummy, QueryBelarusBankService.BY_ID);
      if (index >= 0) {
        FilialRateDTO found = rates[index];
        if (currencySet == null || currencySet.isEmpty()) result.put(filialId, found.rates);
        else result.put(filialId, found.rates.entrySet().stream()
            .filter(e -> currencySet.contains(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
      }
    }
    return result;
  }
}
