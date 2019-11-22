package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.example.demo.model.local.Filial;
import com.example.demo.model.local.FilialDetails;
import com.example.demo.model.remote.FilialsInfoDTO;

@Service
public class DataLoaderService {
  @Autowired
  private Converter<FilialsInfoDTO, Filial> shortConverter;
  @Autowired
  private Converter<FilialsInfoDTO, FilialDetails> longConverter;
  @Autowired
  private QueryBelarusBankService queryService;

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
