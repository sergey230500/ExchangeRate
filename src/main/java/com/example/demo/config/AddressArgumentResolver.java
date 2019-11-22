package com.example.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.demo.model.local.Address;

public class AddressArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Address.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Address result = new Address();
    result.city = webRequest.getParameter("city");
    result.cityType = webRequest.getParameter("cityType");
    result.street = webRequest.getParameter("street");
    result.streetType = webRequest.getParameter("streetType");
    result.house = webRequest.getParameter("house");

    if (isEmpty(result.city) && isEmpty(result.cityType) && isEmpty(result.street) && isEmpty(result.streetType) && isEmpty(result.house)) return null;

    return result;
  }

  private static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
