package com.example.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NoSuchEntityException;
import com.example.demo.model.local.ErrorContent;

@ControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String TYPE_MISMATCH_MESSAGE_FORMAT = "Failed to convert value '%s' to required type '%s' for %s '%s'";
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return handleExceptionInternal(ex, new ErrorContent(ex.getMessage()), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    MethodParameter methodParameter = null;
    if (ex instanceof MethodArgumentTypeMismatchException) {
      methodParameter = ((MethodArgumentTypeMismatchException) ex).getParameter();
    } else if (ex instanceof MethodArgumentConversionNotSupportedException) {
      methodParameter = ((MethodArgumentConversionNotSupportedException) ex).getParameter();
    }
    if (methodParameter != null) {
      ErrorContent body = null;
      if (methodParameter.hasParameterAnnotation(RequestParam.class)) {
        String requestParamName = methodParameter.getParameterAnnotation(RequestParam.class).name();
        body = new ErrorContent(
            String.format(TYPE_MISMATCH_MESSAGE_FORMAT, ex.getValue(), ex.getRequiredType(), "parameter", requestParamName));
        return handleExceptionInternal(ex, body, headers, status, request);
      } else if (methodParameter.hasParameterAnnotation(PathVariable.class)) {
        String pathVariableName = methodParameter.getParameterAnnotation(PathVariable.class).name();
        body = new ErrorContent(
            String.format(TYPE_MISMATCH_MESSAGE_FORMAT, ex.getValue(), ex.getRequiredType(), "path variable", pathVariableName));
      }
      return handleExceptionInternal(ex, body, headers, status, request);
    }
    return super.handleTypeMismatch(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      Object body,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    if (body == null) body = new ErrorContent(ex.getMessage());
    if (!(body instanceof Map)) body = Collections.singletonMap("error", body);
    if (headers.getContentType() == null) headers.setContentType(MediaType.APPLICATION_JSON);
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
    return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(NoSuchEntityException.class)
  public ResponseEntity<?> handleNoSuchException(NoSuchEntityException ex, WebRequest request) {
    return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

}
