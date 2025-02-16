package com.example.carRental.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentalResponseDTO(
        long id,
        long userId,
        long carId,
        LocalDate rentalStart,
        LocalDate rentalEnd,
        BigDecimal price
) {
}
