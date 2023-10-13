package br.com.rodrigovianna.todolist.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException e) {
    return ResponseEntity.status(e.getStatusCode()).body(e.getMostSpecificCause().getMessage());
  }

}
