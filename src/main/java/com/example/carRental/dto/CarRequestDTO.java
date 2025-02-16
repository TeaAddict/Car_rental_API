package com.example.carRental.dto;

import com.example.carRental.model.Rental;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public record CarRequestDTO(
        @NotNull
        @Size(min = 2, max = 50, message = "Maximum 50 characters")
        String brand,

        @NotNull
        @Size(min = 2, max = 50, message = "Maximum 50 characters")
        String model,

        @NotNull
        @Min(0)
        int year
) {
  @AssertTrue(message = "Car year must be less than current year")
  public boolean isYearLessThanCurrentYear() {
    return year <= LocalDate.now().getYear();
  }
}