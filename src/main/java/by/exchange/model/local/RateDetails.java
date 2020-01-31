package by.exchange.model.local;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import by.exchange.model.ExchangeRate;

public class RateDetails {
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long id;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public Map<String, ExchangeRate> rates;

  public RateDetails() {
  }

  public RateDetails(long id, Map<String, ExchangeRate> rates) {
    this.id = id;
    this.rates = rates;
  }
}
