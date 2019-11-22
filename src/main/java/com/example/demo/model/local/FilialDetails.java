package com.example.demo.model.local;

import java.io.IOException;
import java.util.Set;

import com.example.demo.model.Address;
import com.example.demo.model.FilialService;
import com.example.demo.model.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FilialDetails {
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long id;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public long internalId;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public int filialNum;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String departmentNum;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  public int bankingCenterNum;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String extraNum;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  public Address address;
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public Schedule schedule;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String info;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String bankIdCode;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String payerAccountNumber;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String belAccountNumber;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public String foreignAccountNumber;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Address previousAddress;
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
}
