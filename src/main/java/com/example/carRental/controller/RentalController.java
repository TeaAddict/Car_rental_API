package com.example.carRental.controller;

import com.example.carRental.dto.*;
import com.example.carRental.model.Car;
import com.example.carRental.model.Rental;
import com.example.carRental.model.User;
import com.example.carRental.service.CarService;
import com.example.carRental.service.RentalService;
import com.example.carRental.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RentalController {
  RentalService rentalService;
  CarService carService;
  UserService userService;

  public RentalController(RentalService rentalService, CarService carService, UserService userService) {
    this.rentalService = rentalService;
    this.carService = carService;
    this.userService = userService;
  }

  @PostMapping("/rentals")
  public ResponseEntity<?> rentCar(
          @Valid @RequestBody RentalRequestDTO rentalRequestDTO,
          Authentication authentication
  ) {
    final int baseRate = 1;
    Period period = Period.between(rentalRequestDTO.rentalStart(), rentalRequestDTO.rentalEnd());
    long daysBetween = period.getDays();
    BigDecimal price = BigDecimal.valueOf(daysBetween * baseRate).setScale(2, RoundingMode.HALF_UP);
    Optional<Car> car = carService.findCarById(rentalRequestDTO.carId());
    String userName = authentication.getName();

    Optional<User> user = userService.getUserByUsername(userName);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if (car.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    List<Car> cars = user.get()
            .getRentals()
            .stream()
            .map(Rental::getCar)
            .distinct()
            .filter(c -> c.getStatus().equalsIgnoreCase("RENTED"))
            .toList();
    if (cars.size() >= 2) {
      return ResponseEntity.badRequest().body("User already has rented 2 times");
    }

    if (price.compareTo(rentalRequestDTO.price()) != 0) {
      return ResponseEntity.badRequest().body("Price missmatch, given: " + rentalRequestDTO.price() + " expected: " + price);
    }

    if (car.get().getStatus().equalsIgnoreCase("RENTED")) {
      return ResponseEntity.badRequest().body("Car is already rented");
    }

    car.get().setStatus("RENTED");

    Rental rental = new Rental(
            car.get(),
            user.get(),
            rentalRequestDTO.rentalStart(),
            rentalRequestDTO.rentalEnd(),
            price
    );
    rentalService.saveRental(rental);

    RentalResponseDTO rentalResponseDTO = RentalMapper.toRentalResponseDTO(rental);

    return ResponseEntity.ok().body(rentalResponseDTO);
  }

  @PostMapping("/rentals/return/{rentalId}")
  public ResponseEntity<?> returnCar(@PathVariable long rentalId, Authentication authentication) {
    final BigDecimal baseRate = BigDecimal.valueOf(1);
    String userName = authentication.getName();

    Optional<Rental> rental = rentalService.getRentalById(rentalId);
    if (rental.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    final BigDecimal price = rentalService.calculatePrice(rental.get().getRentalStart(), rental.get().getRentalEnd(), baseRate);
    final long carId = rental.get().getCar().getId();

    Optional<User> user = userService.getUserByUsername(userName);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    boolean isOwner = user.get().getRentals().stream().anyMatch(r -> r.getId() == rentalId);

    if (!isOwner) {
      return ResponseEntity.badRequest().body("Can only return your own car");
    }

    Optional<Car> car = carService.findCarById(carId);
    if (car.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if (car.get().getStatus().equalsIgnoreCase("AVAILABLE")) {
      return ResponseEntity.badRequest().body("Car is not rented");
    }

    rental.get().setPrice(price);
    rentalService.saveRental(rental.get());

    car.get().setStatus("AVAILABLE");
    carService.saveCar(car.get());

    CarResponseDTO carResponseDTO = CarMapper.toCarResponseDTO(car.get());

    return ResponseEntity.ok(carResponseDTO);
  }

  @GetMapping("/rentals/my")
//  public ResponseEntity<List<RentalResponseDTO>> getMyRentals(Authentication authentication) {
  public ResponseEntity<?> getMyRentals(Authentication authentication) {
//    long userId = ((User) authentication.getPrincipal()).getId();
    String userName = authentication.getName();
    Optional<User> user = userService.getUserByUsername(userName);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    List<Rental> rentals = user.get().getRentals();
    List<RentalResponseDTO> rentalResponseDTOS = rentals.stream().map(RentalMapper::toRentalResponseDTO).toList();

    return ResponseEntity.ok(rentalResponseDTOS);
  }

  @GetMapping("/rentals/history")
  public ResponseEntity<List<RentalResponseDTO>> getHistory() {
    List<Rental> rentals = rentalService.getRentals();
    List<RentalResponseDTO> rentalResponseDTOS = rentals.stream().map(RentalMapper::toRentalResponseDTO).toList();

    return ResponseEntity.ok().body(rentalResponseDTOS);
  }
}
