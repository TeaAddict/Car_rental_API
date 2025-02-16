package com.example.carRental.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarResponseDTO(
        @NotNull
        long id,

        @NotNull
        @Size(min = 2, max = 50, message = "Maximum 50 characters")
        String brand,

        @NotNull
        @Size(min = 2, max = 50, message = "Maximum 50 characters")
        String model,

        @NotNull
        @Min(0)
        int year,

        @NotNull
        String status
) {
}
