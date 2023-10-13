package br.com.rodrigovianna.todolist.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class StartDateBeforeEndDateException extends HttpClientErrorException {

  public StartDateBeforeEndDateException() {
    super(HttpStatus.BAD_REQUEST, "Start date must be after end date");
  }
}
