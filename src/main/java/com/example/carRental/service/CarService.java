package com.example.carRental.service;

import com.example.carRental.dto.CarRequestDTO;
import com.example.carRental.model.Car;
import com.example.carRental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

  private final CarRepository carRepository;

  @Autowired
  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<Car> findAllCars() {
    return carRepository.findAll();
  }
  
  public List<Car> findAllCarsByStatus(String status) {
    return carRepository.findAllByStatus(status);
  }

  public Car saveCar(Car car) {
    return carRepository.save(car);
  }

  public boolean existsCarById(long id) {
    return carRepository.existsById(id);
  }

  public Optional<Car> findCarById(long id) {
    return carRepository.findById(id);
  }

  public void deleteCarById(long id) {

  }
}
