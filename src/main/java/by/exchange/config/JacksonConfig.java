package by.exchange.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jacksonCommonCustomizer() {
    return new JaksonCommonCustomizer();
  }

  @Bean
  @Profile("dev")
  public Jackson2ObjectMapperBuilderCustomizer jacksonPrettyPrintCustomizer() {
    return new JacksonPrettyPrintCustomizer();
  }

  private static class JacksonPrettyPrintCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
      builder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
    }
  }

  private static class JaksonCommonCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
      builder.featuresToEnable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }
  }
}
