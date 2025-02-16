package com.example.carRental.controller;

import com.example.carRental.dto.CarMapper;
import com.example.carRental.dto.CarRequestDTO;
import com.example.carRental.dto.CarResponseDTO;
import com.example.carRental.model.Car;
import com.example.carRental.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

  private final CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getCars() {
    return ResponseEntity.ok(carService.findAllCars());
  }

  @GetMapping("/cars/available")
  public ResponseEntity<List<CarResponseDTO>> getAvailableCars() {
    List<Car> availableCars = carService.findAllCarsByStatus("AVAILABLE");

    List<CarResponseDTO> availableCarsResponseDTO = availableCars.stream().map(CarMapper::toCarResponseDTO).toList();

    return ResponseEntity.ok(availableCarsResponseDTO);
  }

  @PostMapping("/cars")
  public ResponseEntity<CarResponseDTO> saveCar(@Valid @RequestBody CarRequestDTO carRequestDTO) {

    Car car = CarMapper.toCar(carRequestDTO);
    Car savedCar = carService.saveCar(car);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedCar.getId())
                            .toUri())
            .body(CarMapper.toCarResponseDTO(savedCar));
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<CarResponseDTO> updateCar(@PathVariable long id, @Valid @RequestBody CarRequestDTO carRequestDTO) {
    Optional<Car> car = carService.findCarById(id);

    if (car.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    CarMapper.updateCarFromDTO(car.get(), carRequestDTO);

    return ResponseEntity.ok(CarMapper.toCarResponseDTO(car.get()));
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<?> deleteCar(@PathVariable long id) {
    Optional<Car> car = carService.findCarById(id);

    if (car.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if (car.get().getStatus().equalsIgnoreCase("RENTED")) {
      return ResponseEntity.badRequest().body("Cannot delete, car is currently: rented");
    }

    carService.deleteCarById(id);

    return ResponseEntity.noContent().build();
  }
}
