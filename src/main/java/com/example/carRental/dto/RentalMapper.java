package com.example.carRental.dto;

import com.example.carRental.model.Rental;
import com.example.carRental.service.CarService;
import com.example.carRental.service.UserService;

public class RentalMapper {

  CarService carService;
  UserService userService;

  public RentalMapper(CarService carService, UserService userService) {
    this.carService = carService;
    this.userService = userService;
  }

  public static RentalResponseDTO toRentalResponseDTO(Rental rental) {
    return new RentalResponseDTO(
            rental.getId(),
            rental.getUser().getId(),
            rental.getCar().getId(),
            rental.getRentalStart(),
            rental.getRentalEnd(),
            rental.getPrice()
    );
  }

}
