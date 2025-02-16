package com.example.carRental.dto;

import com.example.carRental.model.Car;
import com.example.carRental.model.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentalRequestDTO(
        @NotNull
        long carId,
        
        @NotNull
        LocalDate rentalStart,

        @NotNull
        LocalDate rentalEnd,

        @NotNull
        @Min(value = 0, message = "Price cannot be less than 0")
        BigDecimal price
) {
  @AssertTrue(message = "The rental period must be at least 1 full day.")
  public boolean isRentalPeriodValid() {
    return rentalStart != null && rentalEnd != null && rentalEnd.isAfter(rentalStart);
  }
}

//private Car car;
//private User user;
//private Date rentalStart;
//private Date rentalEnd;
//private BigDecimal price;