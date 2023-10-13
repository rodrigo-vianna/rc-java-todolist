package br.com.rodrigovianna.todolist.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class TitleLengthException extends HttpClientErrorException {

  public TitleLengthException() {
    super(HttpStatus.BAD_REQUEST, "Title length must be less than 50 characters");
  }
}
