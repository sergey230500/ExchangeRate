package by.exchange.service;

import by.exchange.exception.NoSuchEntityException;
import by.exchange.model.Address;
import by.exchange.model.local.Filial;
import by.exchange.model.local.GPSCoordinates;
import by.exchange.model.local.RateDetails;
import by.exchange.model.remote.FilialsInfoDTO;
import by.exchange.model.remote.KursExchangeDTO;
import by.exchange.model.remote.RemoteDTO;
import by.exchange.service.CityLocator.CityAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  private List<String> services;

  private CityLocator locator;

  public Map<Long, Filial> getAllFilials() throws IOException {
    if (filials == null) {
      loadFilials();
    }
    return filials;
  }

  // TODO make loading synchronized
  private void processFilial(Long id, Filial filial, Map<Long, RateDetails> allRates, Set<String> allServices) {
    RateDetails details = allRates.get(id);
    filial.rates = details == null ? Collections.emptyMap() : details.rates;
    allServices.addAll(filial.services.value);
  }

  public Map<Long, RateDetails> getAllRates() throws IOException {
    if (rates == null) rates = convertToMap(queryService.getRates(), ratesConverter);
    return rates;
  }

  public List<String> getAllServices() throws IOException {
    if (services == null) {
      loadFilials();
    }
    return services;
  }

  private void loadFilials() throws IOException {
    filials = convertToMap(queryService.getFilials(), shortConverter);
    final Map<Long, RateDetails> allRates = getAllRates();
    final Set<String> allServices = new TreeSet<>();
    filials.forEach((id, filial) -> processFilial(id, filial, allRates, allServices));
    this.services = new ArrayList<>(allServices);

  }

  public List<Filial> findFilials(Set<Long> ids, Set<String> currencies, Address address) throws IOException {
    Map<Long, Filial> scope = getAllFilials();

    if (ids != null) {
      // быстрое фильтрование по ключу; retainAll() нельзя, т.к. изменяет оригинал
      Map<Long, Filial> newScope = new LinkedHashMap<>(ids.size());
      for (Long id: ids)
        if (scope.containsKey(id)) newScope.put(id, scope.get(id));
      scope = newScope;
    }

    Stream<Filial> stream = scope.values().stream();
    if (currencies != null || address != null) {
      stream = stream.filter(filial -> {
        if (currencies != null && !hasIntersection(currencies, filial.getCurrencies())) return false;
        if (address != null && !(addressMatches(filial.address, address) || addressMatches(filial.previousAddress, address)))
          return false;
        return true;
      });
    }

    return stream.collect(Collectors.toList());
  }

  public List<RateDetails> getRates(Set<Long> ids, Set<String> currencies) throws IOException {
    Map<Long, Filial> allFilials = getAllFilials();

    List<RateDetails> result = new ArrayList<>(ids.size());
    for (Long id: ids) {
      Filial filial = allFilials.get(id);
      if (filial == null) throw new NoSuchEntityException(String.format(NoSuchEntityException.NO_FILIAL_MSG, id));
      result.add(new RateDetails(id, intersect(filial.rates, currencies)));
    }
    return result;
  }

  public Address findClosestCity(GPSCoordinates location) throws IOException {
    if (locator == null) locator = new CityLocator(getAllFilials().values());
    CityAddress closest = locator.findClosest(location);
    if (closest == null) return null;
    Address result = new Address();
    result.setCityType(closest.cityType);
    result.setCity(closest.city);
    return result;
  }

  private static <S extends RemoteDTO, T> Map<Long, T> convertToMap(S[] rawFilials, Converter<S, T> converter) {
    final int count = rawFilials.length;
    Map<Long, T> result = new LinkedHashMap<>(count);
    for (int i = 0; i < count; ++i)
      result.put(rawFilials[i].id, converter.convert(rawFilials[i]));
    return result;
  }

  private boolean hasIntersection(Set<String> candidate, Set<String> test) {
    // TODO сделать для валют быстрый тест по битовой маске
    if (candidate.isEmpty() || test.isEmpty()) return false;
    for (String aa: candidate)
      if (test.contains(aa.toUpperCase())) return true;
    return false;
  }

  private <T> Map<String, T> intersect(Map<String, T> collection, Set<String> constraint) {
    if (constraint == null || constraint.isEmpty() || collection == null || collection.isEmpty()) return collection;
    Map<String, T> result = new LinkedHashMap<>(collection);
    result.keySet().retainAll(constraint);
    return result;
  }

  private boolean addressMatches(Address subject, Address test) {
    return subject != null && matches(subject.getCityType(), test.getCityType()) &&
        matches(subject.getCity(), test.getCity()) &&
        matches(subject.getStreetType(), test.getStreetType()) &&
        matches(subject.getStreet(), test.getStreet()) &&
        matches(subject.getHouse(), test.getHouse());
  }

  private boolean matches(String subject, String test) {
    return Address.isEmpty(test) || test.equalsIgnoreCase(subject);
  }
}
