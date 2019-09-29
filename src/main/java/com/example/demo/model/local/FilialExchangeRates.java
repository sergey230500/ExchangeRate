package com.example.demo.model.local;

import java.util.Map;

public class FilialExchangeRates {
	public String filialId;
	public Map<String, ExchangeRates> rates;

	public FilialExchangeRates() {

	}

	public FilialExchangeRates(String filialId, Map<String, ExchangeRates> rates) {
		this.filialId = filialId;
		this.rates = rates;
	}
}
