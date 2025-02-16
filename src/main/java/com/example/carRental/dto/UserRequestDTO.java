package com.example.carRental.dto;

import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @Size(min = 2, max = 100, message = "Maximum 100 characters")
        String username,

        @Size(min = 2, max = 100, message = "Maximum 100 characters")
        String password
) {
}
