package br.com.rodrigovianna.todolist.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class EndDateBeforeCurrentDateException extends HttpClientErrorException {

  public EndDateBeforeCurrentDateException() {
    super(HttpStatus.BAD_REQUEST, "End date must be after current date");
  }
}
