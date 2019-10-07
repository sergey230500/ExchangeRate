package com.example.demo.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.remote.FilialRateDTO;

@Service
public class QueryBelarusBankService {
  private static final String REMOTE_API_ROOT = "https://belarusbank.by/api";

  public static final Comparator<FilialRateDTO> BY_ID = Comparator.comparing(f -> f.id);

  @Autowired
  private RestTemplate dataTemplate;

  private FilialRateDTO[] filialRates;

  public void reset() {
    filialRates = null;
  }

  public FilialRateDTO[] getRates() throws IOException {
    if (filialRates == null) {
      filialRates = dataTemplate.getForObject(REMOTE_API_ROOT + "/kursExchange", FilialRateDTO[].class);
      Arrays.sort(filialRates, BY_ID);
    }
    return filialRates;
  }
}
