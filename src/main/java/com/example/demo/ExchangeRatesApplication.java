package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration(exclude = {
    CodecsAutoConfiguration.class,
    ErrorMvcAutoConfiguration.class,
    HttpEncodingAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class,
    JmxAutoConfiguration.class,
    MultipartAutoConfiguration.class,
    SpringApplicationAdminJmxAutoConfiguration.class,
    ValidationAutoConfiguration.class,
    WebSocketServletAutoConfiguration.class })
@ComponentScan
@EnableCaching
public class ExchangeRatesApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExchangeRatesApplication.class, args);
  }

  @Bean
  public RestTemplate dataTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.setConnectTimeout(1000).setReadTimeout(20000).build();
  }

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }
}
