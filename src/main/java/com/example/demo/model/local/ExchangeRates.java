package com.example.demo.model.local;

import java.math.BigDecimal;

public class ExchangeRates {
  public BigDecimal in;
  public BigDecimal out;

  public ExchangeRates() {
  }

  public ExchangeRates(BigDecimal in, BigDecimal out) {
    this.in = in;
    this.out = out;
  }
}
