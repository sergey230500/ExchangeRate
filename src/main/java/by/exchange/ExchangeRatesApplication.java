package by.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableAutoConfiguration(exclude = {
    CodecsAutoConfiguration.class,
    ErrorMvcAutoConfiguration.class,
    HttpEncodingAutoConfiguration.class,
    JmxAutoConfiguration.class,
    MultipartAutoConfiguration.class,
    SpringApplicationAdminJmxAutoConfiguration.class,
    ValidationAutoConfiguration.class,
    WebSocketServletAutoConfiguration.class})
@ComponentScan
@EnableCaching
public class ExchangeRatesApplication {

  private static final Logger LOG = LoggerFactory.getLogger(ExchangeRatesApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ExchangeRatesApplication.class, args);
  }

  @Bean
  public RestTemplate dataTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
        .setConnectTimeout(Duration.ofMillis(1000))
        .setReadTimeout(Duration.ofMillis(20000)).build();
  }

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }

  @EventListener(ContextRefreshedEvent.class)
  public void onContextRefresh(ContextRefreshedEvent event) {
    if (!LOG.isTraceEnabled()) return;
    final ApplicationContext context = event.getApplicationContext();
    StringBuilder sb = new StringBuilder("\n\nActive beans\n\n");
    String[] names = context.getBeanDefinitionNames();
    Arrays.sort(names);
    for (String name: names) {
      Object bean = context.getBean(name);
      sb.append(name).append(" -> ").append(bean.getClass().getName()).append('\n');
    }
    LOG.trace(sb.toString());
  }
}
