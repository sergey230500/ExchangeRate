package by.exchange.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import by.exchange.model.local.Filial;
import by.exchange.service.DataLoaderService;

@RestController
@RequestMapping(path = "/api/direct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectAPIController {
  @Autowired
  private DataLoaderService dataService;

  @JsonView(Filial.Basic.class)
  @GetMapping("/short")
  public Object getShortFormat() throws IOException {
    return dataService.getAllFilials();
  }

  @JsonView(Filial.Detailed.class)
  @GetMapping("/long")
  public Object getLongFormat() throws IOException {
    return dataService.getAllFilials();
  }

  @GetMapping("/gps")
  public Object getCoordinates() throws IOException {
    return dataService.getAllFilials().values().parallelStream()
        .filter(filial -> !filial.gps.isZero())
        .map(filial -> new Object[] { filial.id, filial.gps.longitude, filial.gps.latitude })
        .collect(Collectors.toList());
  }
}
