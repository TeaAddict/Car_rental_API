package com.example.carRental.controller;

import com.example.carRental.dto.CarMapper;
import com.example.carRental.dto.UserMapper;
import com.example.carRental.dto.UserRequestDTO;
import com.example.carRental.dto.UserResponseDTO;
import com.example.carRental.model.Role;
import com.example.carRental.model.User;
import com.example.carRental.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {
    Optional<User> user = userService.getUserById(id);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user.get());

    return ResponseEntity.ok(userResponseDTO);

  }

  @PostMapping("/users")
  public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
    if (userService.existsByUsername(userRequestDTO.username())) {
      return ResponseEntity.badRequest().body("Already exists");
    }

    User user = UserMapper.toUser(userRequestDTO);
    user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
    user.setRoles(List.of(new Role(1, "ROLE_USER")));

    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(UserMapper.toUserResponseDTO(savedUser));
  }
}
