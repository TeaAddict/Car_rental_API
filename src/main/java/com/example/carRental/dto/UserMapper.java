package com.example.carRental.dto;

import com.example.carRental.model.User;

import java.util.List;

public class UserMapper {

  public static User toUser(UserRequestDTO userRequestDTO) {
    return new User(
            userRequestDTO.username(),
            userRequestDTO.password(),
            List.of()
    );
  }

  public static UserResponseDTO toUserResponseDTO(User user) {
    return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getRoles()
    );
  }

  public static List<UserResponseDTO> toUserResponseDTOS(List<User> users) {
    return users.stream().map(UserMapper::toUserResponseDTO).toList();
  }

}
