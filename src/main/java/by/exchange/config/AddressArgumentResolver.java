package by.exchange.config;

import by.exchange.model.Address;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AddressArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Address.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Address resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {

    Address result = new Address();

    result.setCity(webRequest.getParameter("city"));
    result.setCityType(webRequest.getParameter("cityType"));
    result.setStreet(webRequest.getParameter("street"));
    result.setStreetType(webRequest.getParameter("streetType"));
    result.setHouse(webRequest.getParameter("house"));

    return result.isEmpty() ? null : result;
  }
}
