package com.example.carRental.dto;

import com.example.carRental.model.Car;

public class CarMapper {

  public static Car toCar(CarRequestDTO carRequestDTO) {
    return new Car(
            carRequestDTO.brand(),
            carRequestDTO.model(),
            carRequestDTO.year(),
            "AVAILABLE"
    );
  }

  public static CarResponseDTO toCarResponseDTO(Car car) {
    return new CarResponseDTO(
            car.getId(),
            car.getBrand(),
            car.getModel(),
            car.getYear(),
            car.getStatus()
    );
  }

  public static void updateCarFromDTO(Car car, CarRequestDTO carRequestDTO) {
    car.setBrand(carRequestDTO.brand());
    car.setModel(carRequestDTO.model());
    car.setYear(carRequestDTO.year());
  }

}
//private String brand;
//private String model;
//private int year;
//private String status;