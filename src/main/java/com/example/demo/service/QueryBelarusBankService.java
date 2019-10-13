package com.example.demo.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.remote.FilialsInfoDTO;
import com.example.demo.model.remote.KursExchangeDTO;
import com.example.demo.model.remote.RemoteDTO;

@Service
public class QueryBelarusBankService {
  private static final String REMOTE_API_ROOT = "https://belarusbank.by/api";

  public static final Comparator<RemoteDTO> BY_ID = Comparator.comparing(f -> f.id);

  @Autowired
  private RestTemplate dataTemplate;

  private KursExchangeDTO[] filialRates;
  private FilialsInfoDTO[] filialInfo;

  public void reset() {
    filialRates = null;
  }

  public KursExchangeDTO[] getRates() throws IOException {
    if (filialRates == null) {
      filialRates = dataTemplate.getForObject(REMOTE_API_ROOT + "/kursExchange", KursExchangeDTO[].class);
      Arrays.sort(filialRates, BY_ID);
    }
    return filialRates;
  }

  public FilialsInfoDTO[] getFilials() throws IOException {
    if (filialInfo == null) {
      filialInfo = dataTemplate.getForObject(REMOTE_API_ROOT + "/filials_info", FilialsInfoDTO[].class);
      Arrays.sort(filialInfo, BY_ID);
    }
    return filialInfo;
  }

}
