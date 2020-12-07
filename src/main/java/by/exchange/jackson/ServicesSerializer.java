package by.exchange.jackson;

import by.exchange.model.local.Filial;
import by.exchange.service.DataLoaderService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ServicesSerializer extends StdSerializer<Filial.Services> {
  // TODO здесь не нужен целиком DataLoader, да еще с доступом к полю
  private final DataLoaderService dataLoader;

  @Autowired
  public ServicesSerializer(DataLoaderService dataLoader) {
    super(Filial.Services.class);
    this.dataLoader = dataLoader;
  }

  @Override
  public void serialize(Filial.Services value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    List<String> allServices = dataLoader.getAllServices();
    final int count = allServices.size();
    final Set<String> services = new HashSet<>(value.value);
    char[] flags = new char[count];
    for (int i = 0; i < count; ++i)
      flags[i] = services.contains(allServices.get(i)) ? '1' : '0';
    gen.writeString(flags, 0, count);
  }
}
