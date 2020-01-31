package by.exchange.model.local;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import by.exchange.model.Address;
import by.exchange.model.ExchangeRate;
import by.exchange.model.FilialService;
import by.exchange.model.Schedule;

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
  public Services services;

  @JsonSerialize(using = ServicesSerializer.class)
  public static class Services {
    public final Set<FilialService> value;

    public Services(Set<FilialService> value) {
      this.value = value;
    }
  }

  public static class ServicesSerializer extends StdSerializer<Services> {
    public ServicesSerializer() {
      super(Services.class);
    }
    @Override
    public void serialize(Services value, JsonGenerator gen, SerializerProvider provider) throws IOException {
      FilialService[] enums = FilialService.values();
      final int count = enums.length;
      char[] flags = new char[count];
      for (int i = 0; i < count; ++i)
        flags[i] = value.value.contains(enums[i]) ? '1' : '0';
      gen.writeString(flags, 0, count);
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
