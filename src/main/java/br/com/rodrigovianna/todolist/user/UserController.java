package br.com.rodrigovianna.todolist.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel) {
    final var user = this.userRepository.findByUsername(userModel.getUsername());
    if (user != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }

    var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
    userModel.setPassword(passwordHashed);

    final var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

  @GetMapping("/")
  public List<UserModel> list() {
    final var list = this.userRepository.findAll();
    return list;
  }

}
