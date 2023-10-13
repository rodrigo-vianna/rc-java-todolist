package br.com.rodrigovianna.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import br.com.rodrigovianna.todolist.task.exception.EndDateBeforeCurrentDateException;
import br.com.rodrigovianna.todolist.task.exception.StartDateBeforeCurrentDateException;
import br.com.rodrigovianna.todolist.task.exception.StartDateBeforeEndDateException;
import br.com.rodrigovianna.todolist.task.exception.TitleLengthException;
import br.com.rodrigovianna.todolist.task.exception.TitleNullException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(length = 50, nullable = true)
  private String title;
  @Column(nullable = true)
  private String description;
  @Column(nullable = true)
  private LocalDateTime startAt;
  @Column(nullable = true)
  private LocalDateTime endAt;
  @Column(nullable = false)
  private String priority;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public void setTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new TitleNullException();
    }
    if (title.length() > 50) {
      throw new TitleLengthException();
    }
    this.title = title;
  }

  public void validate() {
    final var currentDate = LocalDateTime.now();
    final var startAt = this.getStartAt();
    final var endAt = this.getEndAt();

    if (startAt != null && currentDate.isAfter(startAt)) {
      throw new StartDateBeforeCurrentDateException();
    }
    if (endAt != null) {
      if (currentDate.isAfter(endAt)) {
        throw new EndDateBeforeCurrentDateException();
      }
      if (startAt != null && startAt.isAfter(endAt)) {
        throw new StartDateBeforeEndDateException();
      }
    }
  }
}
