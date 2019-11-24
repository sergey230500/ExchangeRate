package com.example.demo.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
      final Map<Long, RateDetails> allRates = getAllRates();
      filials.forEach((id, filial) -> {
        RateDetails details = allRates.get(id);
        if (details == null) filial.currencies = Collections.emptySet();
        else filial.currencies = new LinkedHashSet<>(details.rates.keySet());
      });
    }
    return filials;
  }

  public Map<Long, RateDetails> getAllRates() throws IOException {
    if (rates == null) rates = convertToMap(queryService.getRates(), ratesConverter);
    return rates;
  }

  public List<Filial> findFilials(Set<Long> ids, Set<String> currencies, Address address) throws IOException {
    Map<Long, Filial> scope = getAllFilials();

    if (ids != null) {
      // быстрое фильтрование по ключу; retainAll() нельзя, т.к. изменяет оригинал
      Map<Long, Filial> newScope = new LinkedHashMap<>(ids.size());
      for (Long id: ids)
        if (scope.containsKey(id)) newScope.put(id.longValue(), scope.get(id));
      scope = newScope;
    }

    Stream<Filial> stream = scope.values().stream();
    if (currencies != null || address != null) {
      stream = stream.filter(filial -> {
        if (currencies != null && !hasIntersection(currencies, filial.currencies)) return false;
        if (address != null) {
          if (!addressMatches(filial.address, address))
            if (filial.previousAddress == null || !addressMatches(filial.previousAddress, address)) return false;
        }
        return true;
      });
    }

    return stream.collect(Collectors.toList());
  }

  public Address findClosestCity(GPSCoordinates location) throws IOException {
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

  private boolean hasIntersection(Set<String> a, Set<String> b) {
    // TODO сделать для валют быстрый тест по битовой маске
    if (a.isEmpty() || b.isEmpty()) return false;
    if (a.size() > b.size()) return hasIntersection(b, a);
    for (String aa: a)
      if (b.contains(aa)) return true;
    return false;
  }

  private boolean addressMatches(Address subject, Address test) {
    if (!Address.isEmpty(test.cityType) && !test.cityType.equalsIgnoreCase(subject.cityType)) return false;
    if (!Address.isEmpty(test.city) && !test.city.equalsIgnoreCase(subject.city)) return false;
    if (!Address.isEmpty(test.streetType) && !test.streetType.equalsIgnoreCase(subject.streetType)) return false;
    if (!Address.isEmpty(test.street) && !test.street.equalsIgnoreCase(subject.street)) return false;
    if (!Address.isEmpty(test.house) && !test.house.equalsIgnoreCase(subject.house)) return false;
    return true;
  }
}
