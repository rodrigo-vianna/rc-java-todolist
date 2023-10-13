package br.com.rodrigovianna.todolist.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class StartDateBeforeCurrentDateException extends HttpClientErrorException {

  public StartDateBeforeCurrentDateException() {
    super(HttpStatus.BAD_REQUEST, "Start date must be after current date");
  }
}
