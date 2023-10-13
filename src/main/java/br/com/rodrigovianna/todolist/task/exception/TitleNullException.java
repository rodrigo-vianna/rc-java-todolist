package br.com.rodrigovianna.todolist.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class TitleNullException extends HttpClientErrorException {

  public TitleNullException() {
    super(HttpStatus.BAD_REQUEST, "Title is required");
  }
}
