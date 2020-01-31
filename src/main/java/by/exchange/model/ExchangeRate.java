package by.exchange.model;

import java.math.BigDecimal;

public class ExchangeRate {
  public BigDecimal in;
  public BigDecimal out;

  public ExchangeRate() {
  }

  public ExchangeRate(BigDecimal in, BigDecimal out) {
    this.in = in;
    this.out = out;
  }
}
