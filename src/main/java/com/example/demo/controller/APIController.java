package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NoSuchEntityException;
import com.example.demo.model.Address;
import com.example.demo.model.FilialService;
import com.example.demo.model.local.Filial;
import com.example.demo.model.local.GPSCoordinates;
import com.example.demo.model.local.RateDetails;
import com.example.demo.model.local.SearchRequest;
import com.example.demo.model.local.SearchResult;
import com.example.demo.service.DataLoaderService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/api", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

  private static final String NO_FILIAL_MSG = "No filial exists with id '%d'";

  @Autowired
  private DataLoaderService dataService;

  /**
   * Текстовый поиск по строке. Поиск ведется по городам, названиям улиц,
   * названиям отделений и id отделений.
   * 
   * @param query
   *          текст, можно несколько слов; регистр не учитывается, слишком
   *          короткие слова выбрасываются
   * @return отранжированный список найденных вариантов, при наличии множества
   *         подходящих вариантов может быть неполным / неточным
   * @see SearchResult
   */
  @RequestMapping(path = "/search")
  public List<SearchResult> search(@RequestParam(name = "q", required = true) String query) {
    SearchRequest request = new SearchRequest(query);
    if (request.isEmpty()) throw new BadRequestException("No valid terms in search query");
    throw new BadRequestException("Not yet implemented");
  }

  /**
   * Поиск отделений по пересечению соответствий. Ответ включает только базовую
   * информацию.
   * 
   * @param ids
   *          набор id отделений; если не пустой, включаются только отделения из
   *          переданного набора
   * @param currencies
   *          набор оперируемых валют; если не пустой, то включаются отделения
   *          хотя бы с одной валютой из набора
   * @param address
   *          адрес поиска; если задан, включаются отделения с точным
   *          совпадением по всем непустым полям
   * @see Filial.Basic
   * 
   * @throws BadRequestException
   *           если ни один входной параметр не задан
   * @return полный точный список подходящих отделений без определенного порядка
   */
  @JsonView(Filial.Basic.class)
  @RequestMapping(path = "/filials")
  public List<Filial> findFilials(
      @RequestParam(name = "id", required = false) Set<Integer> ids,
      @RequestParam(name = "cur", required = false) Set<String> currencies,
      Address address
  // TODO фильтр по расписанию
  ) {
    if ((ids == null || ids.isEmpty()) && (currencies == null || currencies.isEmpty()) && (address == null || address.isEmpty()))
      throw new BadRequestException("At least one parameter must be present");
    return null;
  }

  /**
   * Получение базовой информации по отдельному филиалу. Содержит только
   * ключевые необходимые поля.
   * 
   * @param id
   *          id филиала
   * @see Filial.Basic
   * @throws NoSuchEntityException
   *           если запрошенного филиала не существует
   * @return краткую информацию о филиале
   */
  @JsonView(Filial.Basic.class)
  @RequestMapping(path = "/filials/{id}")
  public Filial getFilial(@PathVariable("id") long id) throws IOException {
    return _getFilial(id);
  }

  /**
   * Получение дополнительной информации по отдельному филиалу. Содержит все
   * данные, не включенные в базовый набор.
   * 
   * @param id
   *          id филиала
   * @see Filial.Detailed
   * @throws NoSuchEntityException
   *           если запрошенного филиала не существует
   * @return дополнительную информацию о филиале
   */
  @JsonView(Filial.Detailed.class)
  @RequestMapping(path = "/filials/{id}/details")
  public Filial getFilialDetails(@PathVariable("id") long id) throws IOException {
    return _getFilial(id);
  }

  private Filial _getFilial(long id) throws IOException, NoSuchEntityException {
    Filial result = dataService.getAllFilials().get(id);
    if (result == null) throw new NoSuchEntityException(String.format(NO_FILIAL_MSG, id));
    return result;
  }

  /**
   * Получение информации по курсам валют для указанных филиалов. Порядок
   * филиалов / валют в ответе в точности соответствует запрашиваемому.
   * 
   * @param ids
   *          набор id отделений
   * @param currencies
   *          набор интересующих валют; если непустой, будут включены только
   *          валюты из набора, если пустой - для каждого филиала все
   *          оперируемые
   * @throws NoSuchEntityException
   *           если хотя бы один из запрошенных филиалов не существует
   * 
   * @see RateDetails
   * @return полный точный список запрошенных филиалов с курсами валют
   */
  @RequestMapping(path = "/rates")
  public List<RateDetails> getRates(
      @RequestParam(name = "id", required = true) Set<Integer> ids,
      @RequestParam(name = "cur", required = false) Set<String> currencies) {
    return null;
  }

  /**
   * Подбор ближайшего населенного пункта по координатам. Учитываются только
   * населенные пункты, указанные в адресах известных филиалов.
   * 
   * @param lon
   *          долгота
   * @param lat
   *          широта
   * @throws BadRequestException
   *           если координаты некорректны или лежат далеко за пределами
   *           покрытого региона
   * 
   * @return адрес с точностью до населенного пункта, как правило тип + имя
   */
  @RequestMapping(path = "/closest")
  public Address findClosest(
      @RequestParam(name = "lon", required = true) double lon,
      @RequestParam(name = "lat", required = true) double lat) throws IOException {
    if (lon < -180 || lon > 180 || lat < -86 || lat > 86) // Web-Mercator projection never returns latitude close to pole 
      throw new BadRequestException("Impossible coordinates");
    return dataService.findClosest(new GPSCoordinates(lon, lat));
  }

  @RequestMapping("/services")
  public FilialService[] getServiceList() throws IOException {
    return FilialService.values();
  }
}
