package com.example.carRental.dto;

import com.example.carRental.model.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserResponseDTO(
        @NotNull
        long id,

        @NotNull
        @Size(min = 2, max = 100, message = "Maximum 100 characters")
        String username,

        List<Role> roles
) {
}
