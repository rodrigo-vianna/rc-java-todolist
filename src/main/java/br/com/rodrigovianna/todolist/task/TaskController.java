package br.com.rodrigovianna.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigovianna.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    final var userId = (UUID) request.getAttribute("userId");
    taskModel.setUserId(userId);
    taskModel.validate();
    final var taskCreated = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
    final var task = this.taskRepository.findById(id).orElse(null);
    if (task == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }
    final var userId = (UUID) request.getAttribute("userId");
    if (!task.getUserId().equals(userId)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to update this task");
    }
    Utils.copyNonNullProperties(taskModel, task);
    taskModel.validate();
    this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.OK).body(taskModel);
  }

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request) {
    final var userId = (UUID) request.getAttribute("userId");
    final var list = this.taskRepository.findByUserId(userId);
    return list;
  }

}
