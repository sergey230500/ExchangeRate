package com.example.demo.model.local;

import java.io.IOException;
import java.util.Set;

import com.example.demo.model.FilialService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class FilialDetails {
  public long id;

  public int filialNum;
  public String departmentNum;
  public int bankingCenterNum;
  public String extraNum;

  public String info;
  public String bankIdCode;
  public String payerAccountNumber;
  public String belAccountNumber;
  public String foreignAccountNumber;
  public Address previousAddress;
  public Services services;

  @JsonSerialize(as = ServicesSerializer.class)
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
