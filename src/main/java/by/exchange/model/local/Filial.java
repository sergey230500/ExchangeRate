package by.exchange.model.local;

import java.util.List;
import java.util.Map;
import java.util.Set;

import by.exchange.jackson.ServicesSerializer;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import by.exchange.model.Address;
import by.exchange.model.ExchangeRate;
import by.exchange.model.Schedule;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Filial {

  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long id;

  @JsonView(Basic.class)
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String name;

  @JsonView(Basic.class)
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public GPSCoordinates gps;

  @JsonView(Detailed.class)
  @JsonIgnore
  public Address address;

  @JsonView(Basic.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String phone;

  @JsonView(Basic.class)
  @JsonInclude(JsonInclude.Include.ALWAYS)
  @JsonGetter
  public Set<String> getCurrencies() {
    return rates.keySet();
  }

  @JsonIgnore
  public Map<String, ExchangeRate> rates;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long internalId;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public int filialNum;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String departmentNum;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public int bankingCenterNum;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String extraNum;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public Schedule schedule;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String info;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String bankIdCode;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String payerAccountNumber;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String belAccountNumber;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String foreignAccountNumber;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Address previousAddress;

  @JsonView(Detailed.class)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ApiModelProperty(dataType = "string")
  public Services services;

  @JsonSerialize(using = ServicesSerializer.class)
  public static class Services {
    public final List<String> value;

    public Services(List<String> value) {
      this.value = value;
    }
  }

  @JsonView(Basic.class)
  @JsonInclude(JsonInclude.Include.ALWAYS)
  @JsonGetter("address")
  public String getAddressValue() {
    return address == null ? null : address.getValue();
  }

  /**
   * Метка для базовых свойтв модели. Включает имя, GPS, список валют, телефон
   * отделения и краткий адрес
   */
  public static interface Basic {
  }

  /**
   * Метка для дополнительных свойтв модели. Включает все свойства кроме: имя,
   * GPS, список валют, телефон отделения; адрес включен полностью.
   */
  public static interface Detailed {
  }
}
