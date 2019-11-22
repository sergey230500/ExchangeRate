package com.example.demo.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.model.local.ErrorResponse;

@ControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String TYPE_MISMATCH_MESSAGE_FORMAT = "Failed to convert value '%s' to required type '%s' for parameter '%s'";
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, new ErrorResponse(ex.getMessage()), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    MethodParameter methodParameter = null;
    if (ex instanceof MethodArgumentTypeMismatchException) {
      methodParameter = ((MethodArgumentTypeMismatchException) ex).getParameter();
    } else if (ex instanceof MethodArgumentConversionNotSupportedException) {
      methodParameter = ((MethodArgumentConversionNotSupportedException) ex).getParameter();
    }
    if (methodParameter != null && methodParameter.hasParameterAnnotation(RequestParam.class)) {
      String requestParamName = methodParameter.getParameterAnnotation(RequestParam.class).name();
      ErrorResponse body = new ErrorResponse(String.format(TYPE_MISMATCH_MESSAGE_FORMAT, ex.getValue(), ex.getRequiredType(), requestParamName));
      return handleExceptionInternal(ex, body, headers, status, request);
    }
    return super.handleTypeMismatch(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    if (body == null) body = new ErrorResponse(ex.getMessage());
    if (headers.getContentType() == null) headers.setContentType(MediaType.APPLICATION_JSON);
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }
}
