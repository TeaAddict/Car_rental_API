package com.example.carRental.service;

import com.example.carRental.model.Rental;
import com.example.carRental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

  private final RentalRepository rentalRepository;

  @Autowired
  public RentalService(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  public Rental saveRental(Rental rental) {
    return rentalRepository.save(rental);
  }

  public Optional<Rental> getRentalById(long id) {
    return rentalRepository.findById(id);
  }

  public List<Rental> getRentals() {
    return rentalRepository.findAll();
  }

  public BigDecimal calculatePrice(LocalDate start, LocalDate end, BigDecimal baseRate) {
    Period period = Period.between(start, end);
    BigDecimal daysBetween = BigDecimal.valueOf(period.getDays());
    return daysBetween.multiply(baseRate).setScale(2, RoundingMode.HALF_UP);
  }
}
